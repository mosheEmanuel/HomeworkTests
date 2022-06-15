package com.example.homeworktests;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SqlLiteHelperHomework sql = new SqlLiteHelperHomework(context);
        sql.open();
        if (!sql.isEmpty()) {
            String title = "יש לך שיעורי בית";
            String message = "תבדוק איזה שעורי בית יש לך";

            int icon = R.drawable.ic_baseline_access_time_24;

            //phase 2
            long currentTimeMillis = System.currentTimeMillis();
            Intent intent1 = new Intent(context, MenuActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);// service


            String channelId = "channelId";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelId");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelId);
            }
            //phase 3
            Notification notification = builder.setContentIntent(pendingIntent)
                    .setWhen(currentTimeMillis).setContentTitle(title).setSmallIcon(icon)
                    .setAutoCancel(true).
                    setStyle(new NotificationCompat.BigTextStyle().bigText(message)).build();
            notificationManager.notify(0, notification);//יצירת ההתראה
        }
    }
}