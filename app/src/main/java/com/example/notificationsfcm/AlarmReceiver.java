package com.example.notificationsfcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent dialogIntent = new Intent(context, AlarmActivity.class);
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(dialogIntent);
//        Intent alarmIntent = new Intent(context, AlarmActivity.class);
//        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(alarmIntent);
//        NotificationHelper.sendNotification(context, "Alarm Notification", "This is your scheduled notification.");
//        Log.d("notify","set");
Intent serviceIntent=new Intent(context,PopupService.class);
serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
context.startService(serviceIntent);

    }
}
