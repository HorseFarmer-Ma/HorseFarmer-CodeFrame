package com.meizu.code.frame.base.model.widget.nightmode;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 夜间模式ImageView
 * <p>
 * Created by mxm on 14/01/18.
 */

public class NightModeImageView extends AppCompatImageView implements NightModeView {

    public NightModeImageView(Context context) {
        this(context, null);
    }

    public NightModeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NightModeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    public void applyNightMode(boolean isNight) {

    }
}
