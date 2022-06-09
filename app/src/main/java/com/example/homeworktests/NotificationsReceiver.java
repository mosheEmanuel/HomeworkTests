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
import androidx.core.app.NotificationManagerCompat;

public class NotificationsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        //phase 2
        Intent intent1 = new Intent(context, MenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);//חיבור לservice של המערכת

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "M_CH_ID");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//אם הגרסה מעל אוראו חובה להוסיף channel
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        //phase 3
//        Notification notification = builder.setContentIntent(pendingIntent)
//                .setSmallIcon(icon).setTicker(ticker).setWhen(when)
//                .setAutoCancel(true).setContentTitle(title)
//                .setContentText(message).build();
//        notificationManager.notify(3, notification);//יצירת האובייקט של ההתראה
    }
}