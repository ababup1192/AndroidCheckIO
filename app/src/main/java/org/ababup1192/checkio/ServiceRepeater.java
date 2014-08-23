package org.ababup1192.checkio;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class ServiceRepeater {

    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent servicePendingIntent;

    public ServiceRepeater(Context context, Intent serviceIntent) {
        this.context = context;
        this.servicePendingIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void start(long delayMinutes, long intervalMinutes) {
        // long型でミリ秒で扱う必要がある。
        // java.util.concurrent.TimeUnitクラスが便利な「分→ミリ秒」変換メソッドを持っている。
        long startTime = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(delayMinutes);
        long interval = TimeUnit.MINUTES.toMillis(intervalMinutes);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, servicePendingIntent);
    }

    public void start(long intervalMinutes) {
        start(0, intervalMinutes);
    }

    public void stop() {
        if (servicePendingIntent != null && alarmManager != null) {
            alarmManager.cancel(servicePendingIntent);
        }
    }

}
