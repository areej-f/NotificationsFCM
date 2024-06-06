package com.example.notificationsfcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    public static void sendNotification(Context context, String title, String message) {

        String channelId = "alarm_channel";
        String channelName = "Alarm Notification";
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent yesIntent=new Intent(context, MainActivity.class);
        PendingIntent yesPendingIntent=PendingIntent.getActivity(context,0,yesIntent,PendingIntent.FLAG_IMMUTABLE);

        Intent noIntent=new Intent(context, MainActivity.class);
        PendingIntent noPendingIntent=PendingIntent.getActivity(context,0,noIntent,PendingIntent.FLAG_IMMUTABLE);


        // Create notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .addAction(R.drawable.ic_launcher_foreground,"YES",yesPendingIntent)
                .addAction(R.drawable.ic_launcher_foreground,"NO",noPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
        Log.d("Show","show");
    }
}

