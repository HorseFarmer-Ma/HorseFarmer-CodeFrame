package com.meizu.code.frame.base.mvp;

import com.meizu.code.frame.base.annotations.AnnotationsHelper;

import java.util.HashMap;
import java.util.UUID;

/**
 * 获取Present
 *
 * Created by maxueming on 17-11-16.
 */

public class PresenterManager<M extends BasePresenter>{

    public static final String NON_PRESENTER_ID = "-1";
    private static PresenterManager mInstance;
    private HashMap<String, M> mPresenters;

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

    public <N extends BeamView> BasePresenter createPresenter(String presenterId, N view) {
        M presenter;
        if (presenterId.equals(NON_PRESENTER_ID)) {
            presenter = AnnotationsHelper.createPresenterClass(view);
            // 生成Key，存presenter
            String newPresentId = generatePresenterId();
            presenter.setPresenterId(newPresentId);
        } else {
            presenter = mPresenters.get(presenterId);
        }
        if (presenter != null) {
            presenter.setBeamView(view);
        }
        return presenter;
    }

    protected void destroyPresenter(String presenterId) {
        if (presenterId.equals(NON_PRESENTER_ID)) {
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
