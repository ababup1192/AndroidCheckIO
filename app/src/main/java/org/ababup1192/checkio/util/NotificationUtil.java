package org.ababup1192.checkio.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import org.ababup1192.checkio.R;

/**
 * ステータスバー通知のユーティリティ
 */
public class NotificationUtil {

    /**
     * ステータスバー通知を行う
     *
     * @param context     Context
     * @param intent      通知をクリックしたときに開かれるActivityIntent
     * @param tickerText  通知バーに表示する簡易メッセージ
     * @param titleText   通知のタイトル
     * @param contentText 通知内容
     */
    public static void notify(Context context, Intent intent, String tickerText, String titleText, String contentText) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher)    // アイコン
                .setTicker(tickerText)                   // 通知バーに表示する簡易メッセージ
                .setWhen(SystemClock.elapsedRealtime())     // 時間
                .setContentTitle(titleText)              // 展開メッセージのタイトル
                .setContentText(contentText)             // 展開メッセージの詳細メッセージ
                .setContentIntent(pendingIntent);        // 通知クリックによって開かれるIntent

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) SystemClock.elapsedRealtime(), notificationBuilder.build());
    }

}
