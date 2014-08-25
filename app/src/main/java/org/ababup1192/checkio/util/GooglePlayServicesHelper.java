package org.ababup1192.checkio.util;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * GooglePlayService利用のための準備ヘルパー
 */
public class GooglePlayServicesHelper {

    /**
     * GooglePlayServiceが利用可能かを調べ、利用出来なければエラーダイアログを表示
     * @param context Context
     * @param activity Activity ダイアログ表示のため
     */
    public static void checkGooglePlayServicesAvailable(Context context, Activity activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (isError(resultCode)) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 1).show();
        }
    }

    /**
     * GooglePlayService利用のエラー検出
     * @param resultCode 検出コード
     * @return エラーならtrue
     */
    public static boolean isError(int resultCode) {
        return (resultCode == ConnectionResult.SERVICE_MISSING ||
                resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                resultCode == ConnectionResult.SERVICE_DISABLED);
    }
}
