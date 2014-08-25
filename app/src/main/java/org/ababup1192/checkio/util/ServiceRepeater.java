package org.ababup1192.checkio.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * ServiceIntentの繰り返しを行う。
 * startメソッドで繰り返し開始。
 * stopメソッドで繰り返し終了。
 */
public class ServiceRepeater {

    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent servicePendingIntent;

    /**
     * コンストラクタ
     * @param context Context
     * @param serviceIntent ServiceのIntent
     */
    public ServiceRepeater(Context context, Intent serviceIntent) {
        this.context = context;
        this.servicePendingIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * ServiceIntentの繰り返し開始 開始遅延あり
     * @param delayMinutes 開始遅延(分)
     * @param intervalMinutes 繰り返し間隔(分)
     */
    public void start(long delayMinutes, long intervalMinutes) {
        // long型でミリ秒で扱う必要がある。
        // java.util.concurrent.TimeUnitクラスが便利な「分→ミリ秒」変換メソッドを持っている。
        long startTime = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(delayMinutes);
        long interval = TimeUnit.MINUTES.toMillis(intervalMinutes);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, servicePendingIntent);
    }

    /**
     * ServiceIntentの繰り返し開始 開始遅延なし
     * @param intervalMinutes 繰り返し間隔(分)
     */
    public void start(long intervalMinutes) {
        start(0, intervalMinutes);
    }

    /**
     * ServiceIntentの停止
     */
    public void stop() {
        if (servicePendingIntent != null && alarmManager != null) {
            alarmManager.cancel(servicePendingIntent);
        }
    }

}
