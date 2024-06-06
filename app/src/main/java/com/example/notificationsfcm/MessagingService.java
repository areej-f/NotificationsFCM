package com.example.notificationsfcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "MyNotificationChannel";
    int notificationId=123;
    Context context;

    @Override
    public void onCreate() {

        createNotificationChannel();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d("Message received","message");
        if (message.getNotification() != null) {
            showNotification(message.getNotification().getTitle(), message.getNotification().getBody());
        }

        // check if the message contains data payload
        if (message.getData().size() > 0) {
            // Handle data payload
            handleDataMessage(message.getData());
        }
    }

    private void handleDataMessage(Map<String, String> data) {
        // Extract notification details from data payload
        String title = data.get("title");
        String body = data.get("body");
        showNotification(title,body);
        // Additional data for buttons or actions
        // ...
    }
    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent yesIntent = new Intent(context, NotificationActionReceiver.class);
        yesIntent.setAction("ACTION_YES");
        yesIntent.putExtra("notificationId", notificationId);
        PendingIntent yesPendingIntent = PendingIntent.getBroadcast(context, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent noIntent = new Intent(context, NotificationActionReceiver.class);
        noIntent.setAction("ACTION_NO");
        noIntent.putExtra("notificationId", notificationId);
        PendingIntent noPendingIntent = PendingIntent.getBroadcast(context, 0, noIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.custom_notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .addAction(R.drawable.ic_launcher_foreground,"View",yesPendingIntent)
                .addAction(R.drawable.ic_launcher_foreground,"Dismiss",noPendingIntent)
//                .setCustomContentView(remoteViews)
//                .setCustomBigContentView(remoteViews)
                .setAutoCancel(true);
//remoteViews.setTextViewText(R.id.txtTitle,title);



//        remoteViews.setOnClickPendingIntent(R.id.btn_yes, yesPendingIntent);
//        remoteViews.setOnClickPendingIntent(R.id.btn_no, noPendingIntent);


        // Show the notification
        notificationManager.notify(notificationId, builder.build());
        Log.d("NotificationHelper", "Notification displayed with ID: " + notificationId);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is my notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
