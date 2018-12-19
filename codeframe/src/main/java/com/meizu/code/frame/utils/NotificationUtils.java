/*
 * ************************************************************
 * Class：NotificationUtils.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-12-04 13:43:52
 * Last modified time：2018-12-04 13:43:51
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {

    public static void createNotification(Context context, Intent targetIntent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, "100")
                .setContentTitle("邀请你")
                .setContentText("骚气测试一波")
                .setShowWhen(true)
                .setAutoCancel(true)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setFullScreenIntent(pendingIntent, false)
                .setDeleteIntent(null)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND);
//                .setSmallIcon(R.mipmap.ic_launcher);// TODO: Pic res not supplied
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {//8.0系统之上
            String channelId = "testProtobuf";
            NotificationChannel channel = new NotificationChannel(channelId, "TestProtobuf", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        Notification notification = builder.build();
        manager.notify(100, notification);//显示通知
    }
}
