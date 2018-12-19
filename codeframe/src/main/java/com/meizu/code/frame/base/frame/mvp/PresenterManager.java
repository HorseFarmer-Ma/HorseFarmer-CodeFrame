package com.meizu.code.frame.base.frame.mvp;

import android.text.TextUtils;

import com.meizu.code.frame.base.annotations.AnnotationsHelper;

import java.util.HashMap;
import java.util.UUID;

/**
 * 获取Present
 * <p>
 * Created by maxueming on 17-11-16.
 */

public class PresenterManager {

    public static final String NON_PRESENTER_ID = "-1";
    private static PresenterManager mInstance;
    private HashMap<String, BasePresenter> mPresenters;

    public PresenterManager() {
        mPresenters = new HashMap<>();
    }

    public static PresenterManager getInstance() {
        if (mInstance == null) {
            synchronized (PresenterManager.class) {
                if (mInstance == null) {
                    mInstance = new PresenterManager();
                }
            }
        }
        return mInstance;
    }

    public <M extends BasePresenter> M createPresenter(String presenterId, BeamView beamView) {
        M presenter;
        if (presenterId.equals(NON_PRESENTER_ID)) {
            presenter = AnnotationsHelper.createPresenterClass(beamView);
            // 生成Key，存presenter
            String newPresentId = generatePresenterId();
            presenter.setPresenterId(newPresentId);
            mPresenters.put(newPresentId, presenter);
        } else {
            presenter = (M) mPresenters.get(presenterId);
        }
        if (presenter != null) {
            presenter.setBeamView(beamView);
        }
        return presenter;
    }

    protected void destroyPresenter(String presenterId) {
        if (TextUtils.equals(presenterId, NON_PRESENTER_ID)) {
            return;
        }
        boolean isContainId = mPresenters.containsKey(presenterId);
        if (isContainId) {
            mPresenters.remove(presenterId);
        }
    }

    private String generatePresenterId() {
        return UUID.randomUUID().toString();
    }
}
