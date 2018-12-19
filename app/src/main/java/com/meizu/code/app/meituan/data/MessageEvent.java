/*
 * ************************************************************
 * Class：MessageEvent.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-21 09:23:38
 * Last modified time：2018-11-21 09:23:37
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.data;

public class MessageEvent {

    public static int MESSAGE_UPDATE_BAL_DATA = 100;

    private int message;

    public MessageEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}