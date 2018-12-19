/*
 * ************************************************************
 * Class：SharePreManager.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-20 09:22:01
 * Last modified time：2018-11-20 09:22:01
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * SP Manager
 */
public class SharePreManager {

    private static final int MAX_SHARE_PRE_ENTITY_COUNT = 4;        // 最多保留的SP实体类

    private final Map<String, SharePreEntity> mSharePreEntityMap = new HashMap<>();

    private SharePreManager() {
    }

    private static class Instance {
        private static final SharePreManager HOLDER = new SharePreManager();
    }

    public static SharePreManager getInstance() {
        return Instance.HOLDER;
    }

    /**
     * Get SP Entity
     * <p>
     * {@link #MAX_SHARE_PRE_ENTITY_COUNT}
     */
    public SharePreEntity getSPEntity(String tableName) {
        if (!mSharePreEntityMap.containsKey(tableName)) {
            synchronized (SharePreManager.class) {
                if (!mSharePreEntityMap.containsKey(tableName)) {
                    if (mSharePreEntityMap.size() > MAX_SHARE_PRE_ENTITY_COUNT) {
                        Set<String> keySet = mSharePreEntityMap.keySet();
                        if (!CollectionUtils.isEmpty(keySet)) {
                            mSharePreEntityMap.remove(keySet.iterator().next());
                        }
                    }
                    mSharePreEntityMap.put(tableName, new SharePreEntity(tableName));
                }
            }
        }
        return mSharePreEntityMap.get(tableName);
    }

    public void clearAllSPEntity() {
        mSharePreEntityMap.clear();
    }

    // 数据存储实体类
    public static class SharePreEntity {

        private final SharedPreferences mSharedPreference;
        private final SharedPreferences.Editor mEditor;

        @SuppressLint("CommitPrefEdits")
        private SharePreEntity(String tableName) {
            mSharedPreference = CodeFrameUtils.getInstance().getGlobalContext()
                    .getSharedPreferences(tableName, Context.MODE_PRIVATE);
            mEditor = mSharedPreference.edit();
        }

        /**
         * 保存字符串数据
         *
         * @param key：键值
         * @param value：数值
         * @return 结果
         */
        public boolean putString(String key, String value) {
            mEditor.remove(key);
            mEditor.putString(key, value);
            return mEditor.commit();
        }

        /**
         * 保存整型数据
         *
         * @param key：键值
         * @param value：数值
         * @return 结果
         */
        public boolean putInt(String key, int value) {
            mEditor.remove(key);
            mEditor.putInt(key, value);
            return mEditor.commit();
        }

        /**
         * 保存长整型数据
         *
         * @param key：键值
         * @param value：数值
         * @return 结果
         */
        public boolean putLong(String key, long value) {
            mEditor.remove(key);
            mEditor.putLong(key, value);
            return mEditor.commit();
        }

        /**
         * 保存浮点数据
         *
         * @param key：键值
         * @param value：数值
         * @return 结果
         */
        public boolean putFloat(String key, float value) {
            mEditor.remove(key);
            mEditor.putFloat(key, value);
            return mEditor.commit();
        }

        /**
         * 保存布尔型数据
         *
         * @param key：键值
         * @param value：数值
         * @return 结果
         */
        public boolean putBoolean(String key, boolean value) {
            mEditor.remove(key);
            mEditor.putBoolean(key, value);
            return mEditor.commit();
        }

        /**
         * 读取字符串数据
         *
         * @param key：键值
         * @param def：默认值
         * @return 读取值
         */
        public String getString(String key, String def) {
            return mSharedPreference.getString(key, def);
        }

        /**
         * 读取整型数据
         *
         * @param key：键值
         * @param def：默认值
         * @return 读取值
         */
        public int getInt(String key, int def) {
            return mSharedPreference.getInt(key, def);
        }

        /**
         * 读取长整型数据
         *
         * @param key：键值
         * @param def：默认值
         * @return 读取值
         */
        public long getLong(String key, long def) {
            return mSharedPreference.getLong(key, def);
        }

        /**
         * 读取浮点数据
         *
         * @param key：键值
         * @param def：默认值
         * @return 读取值
         */
        public float getFloat(String key, float def) {
            return mSharedPreference.getFloat(key, def);
        }

        /**
         * 读取布尔型数据
         *
         * @param key：键值
         * @param def：默认值
         * @return 读取值
         */
        public boolean getBoolean(String key, boolean def) {
            return mSharedPreference.getBoolean(key, def);
        }
    }
}
