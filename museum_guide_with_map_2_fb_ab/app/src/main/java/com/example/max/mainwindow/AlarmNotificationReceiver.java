package com.example.max.mainwindow;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.max.mainwindow.museumpackage.ListFragment;


/**
 * Created by reale on 24/11/2016.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, ListFragment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.pic_ic_museum)
                .setContentTitle("Вы свободны сегодня вечером?")
                .setContentText("Если у вас есть свободное время, то почему бы не посвятить его культурному обогащению? Одберите в нашем каталоге музей себе по вкусу.") // Текст уведомления
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher)) // большая
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, builder.build());

    }
}
