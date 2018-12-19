/*
 * ************************************************************
 * Class：FingerprintHelper.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-12-17 21:18:26
 * Last modified time：2018-12-17 21:18:25
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.meizu.code.frame.base.frame.common.log.Logger;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHelper {
    private static final String TAG = "FingerprintHelper";
    private static final String KEY_NAME = "finger_print_key";

    private static FingerprintHelper sInstance;

    private FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;
    private Cipher mCipher;
    private KeyStore mKeyStore;

    private FingerprintHelper() {
        mFingerprintManager = CodeFrameUtils.getInstance().getGlobalContext().getSystemService(FingerprintManager.class);
        initKey();
    }

    private static class Holder {
        private static final FingerprintHelper INSTANCE = new FingerprintHelper();
    }

    public static FingerprintHelper getInstance() {
        return Holder.INSTANCE;
    }

    private void initKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSupportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            ToastUtils.showToast("您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT);
            return false;
        } else {
            Context context = CodeFrameUtils.getInstance().getGlobalContext();
            KeyguardManager keyguardManager = context.getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = context.getSystemService(FingerprintManager.class);
            if (fingerprintManager != null && !fingerprintManager.isHardwareDetected()) {
                ToastUtils.showToast("您的手机不支持指纹功能", Toast.LENGTH_SHORT);
                return false;
            } else if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
                ToastUtils.showToast("您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT);
                return false;
            } else if (fingerprintManager != null && !fingerprintManager.hasEnrolledFingerprints()) {
                ToastUtils.showToast("您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT);
                return false;
            }
        }
        return true;
    }

    public void setCipher(Cipher cipher) {
        mCipher = cipher;
    }

    public Cipher getCipher() {
        if (mCipher == null) {
            try {
                SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
                Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } catch (Exception e) {
                Logger.logE(TAG, "getCipher error: " + e);
            }
        }
        return mCipher;
    }
}
