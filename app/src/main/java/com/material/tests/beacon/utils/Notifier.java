package com.material.tests.beacon.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.material.tests.beacon.MonitoringActivity;
import com.material.tests.beacon.R;

/**
 * Created by developer on 27.12.2014.
 */
public class Notifier {

    public static void generateNotification(Context context, String message) {

        Intent launchIntent = new Intent(context, MonitoringActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(
                0,
                new NotificationCompat.Builder(context).setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_launcher).setTicker(message)
                        .setContentTitle(context.getString(R.string.app_name)).setContentText(message)
                        .setContentIntent(PendingIntent.getActivity(context, 0, launchIntent, 0)).setAutoCancel(true)
                        .build());

    }

}
