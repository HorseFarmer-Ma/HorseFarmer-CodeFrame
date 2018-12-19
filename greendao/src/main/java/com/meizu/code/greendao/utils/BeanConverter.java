/*
 * ************************************************************
 * Class：BeanConverter.java  Module：greendao  Project：CodeFrame
 *
 * Current modified time：2018-10-12 19:26:46
 * Last modified time：2018-09-04 11:49:40
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.greendao.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Collections;
import java.util.List;

/**
 * Bean 转换器
 * <p>
 * Created by mxm on 21:54.
 */

public class BeanConverter implements PropertyConverter<List, String> {
    private static final String TAG = "BeanConverter";

    @Override
    public List convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            try {
                return JSON.parseArray(databaseValue);
            } catch (Exception e) {
                Log.e(TAG, "convertToEntityProperty error : " + e);
                return null;
            }
        }
    }

    @Override
    public String convertToDatabaseValue(List entityProperty) {
        if (entityProperty == null) {
            entityProperty = Collections.emptyList();
        }
        return JSON.toJSONString(entityProperty);
    }
}
