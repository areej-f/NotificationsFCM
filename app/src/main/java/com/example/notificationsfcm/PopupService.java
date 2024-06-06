package com.example.notificationsfcm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class PopupService extends Service {
    final String TAG="PopUpservice";
    AlarmManager alarmManager;
    PendingIntent alarmIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Service Created");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ScheduleExactAlarm")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Service Started");
//        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent aintent = new Intent(this, AlarmReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(this, 0, aintent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        Log.d("Alarm Schedule", "Alarm set");
//         Schedule the alarm
//        long triggerTime = System.currentTimeMillis() + 60000; // 1 minute from now

//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, alarmIntent);
        showDialog();
        return START_STICKY;
    }

    private void showDialog() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
       WindowManager.LayoutParams params= new WindowManager.LayoutParams(
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
               WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
               WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
       );

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        dialogView.findViewById(R.id.button_cancel).setOnClickListener(v -> {
            windowManager.removeView(dialogView);
            stopSelf();
        });


        dialogView.findViewById(R.id.button_ok).setOnClickListener(v -> {
            Intent trackIntent=new Intent(this,Orders.class);
            trackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(trackIntent);
            windowManager.removeView(dialogView);
            stopSelf();
        });
        windowManager.addView(dialogView, params);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }
}
