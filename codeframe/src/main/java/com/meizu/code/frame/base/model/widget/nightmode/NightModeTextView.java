package com.meizu.code.frame.base.model.widget.nightmode;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 夜间模式TextView
 * <p>
 * Created by mxm on 14/01/18.
 */

public class NightModeTextView extends AppCompatTextView implements NightModeView {

    public NightModeTextView(Context context) {
        this(context, null);
    }

    public NightModeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NightModeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    public void applyNightMode(boolean isNight) {

    }
}
