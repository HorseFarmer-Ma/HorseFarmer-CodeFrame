/*
 * ************************************************************
 * Class：SpConstants.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-20 10:16:32
 * Last modified time：2018-11-20 10:16:32
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.data;

import com.meizu.code.frame.utils.SharePreManager;

public class MeituanSpConstants {
    private static int sTargetCount;

    static {
        SharePreManager.SharePreEntity spEntity = SharePreManager.getInstance()
                .getSPEntity(MeituanSpTableparam.TableSetting.TABLE_SETTING);
        sTargetCount = spEntity.getInt(MeituanSpTableparam.TableSetting.KEY_TARGET_BAL, 30);
    }

    public static int getTargetCount() {
        return sTargetCount;
    }

    public static void setTargetCount(int targetCount) {
        sTargetCount = targetCount;
        SharePreManager.SharePreEntity spEntity = SharePreManager.getInstance()
                .getSPEntity(MeituanSpTableparam.TableSetting.TABLE_SETTING);
        spEntity.putInt(MeituanSpTableparam.TableSetting.KEY_TARGET_BAL, targetCount);
    }
}
