/*
 * ************************************************************
 * Class：ToastUtils.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-19 15:26:55
 * Last modified time：2018-11-19 15:26:54
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

public class ToastUtils {

    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String toast, int duration) {
        if (mToast == null) {
            synchronized (ToastUtils.class) {
                if (mToast == null) {
                    mToast = Toast.makeText(CodeFrameUtils.getInstance().getGlobalContext(), null, duration);
                }
            }
        }
        mToast.setText(toast);
        mToast.setDuration(duration);
        mToast.show();
    }
}
