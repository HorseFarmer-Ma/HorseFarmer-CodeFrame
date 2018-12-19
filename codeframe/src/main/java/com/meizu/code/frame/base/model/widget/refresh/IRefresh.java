package com.meizu.code.frame.base.model.widget.refresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 刷新状态接口
 * <p>
 * Author: maxueming 502048
 * Date: 18-8-3
 */

public interface IRefresh {
    interface State {
        int RESET = 0;          // 初始态
        int PULL = 1;           // 下拉态
        int REFRESHING = 2;     // 刷新态
        int COMPLETED = 3;      // 完成态
    }

    @IntDef({State.RESET, State.PULL, State.REFRESHING, State.COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    @interface RefreshState {
    }

    void refreshComplete();

    void scrollToRefresh();
}
