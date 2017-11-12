package com.meizu.code.frame.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meizu.code.frame.R;
import com.orhanobut.logger.Logger;

public abstract class BaseActivity<T extends BaseView> extends AppCompatActivity {

    protected T mBeamView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ensureCreateBeamView();
        ViewGroup frameLayout = createBaseLayout();
        setContentView(frameLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBeamView.doCreate(this, savedInstanceState);
        frameLayout.addView(mBeamView.onCreateView(frameLayout, LayoutInflater.from(this)));
    }

    /**
     * 生成底层View
     */
    private ViewGroup createBaseLayout() {
        FrameLayout baseLayout = new FrameLayout(this);
        baseLayout.setFitsSystemWindows(fitsSystemWindows());
        return baseLayout;
    }

    protected boolean fitsSystemWindows() {
        return true;
    }

    private void ensureCreateBeamView() {
        if (mBeamView == null) {
            mBeamView = createBeamView();
        }
    }

    private T createBeamView() {
        try {
            mBeamView = getBeamViewClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new Throwable("FAILED: getBeamViewClass");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                Logger.e("NULL POINTER: mBeamView");
            }
        }

        return mBeamView;
    }

    protected abstract Class<T> getBeamViewClass();

    @Override
    protected void onStart() {
        super.onStart();
        mBeamView.onStart();
    }

    /**
     * 重载Bundle数据
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mBeamView.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBeamView.onResume();
    }

    /**
     * 保存bundle数据，在OnDestroy之前调用
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBeamView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBeamView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBeamView.onDestroy();
    }
}
