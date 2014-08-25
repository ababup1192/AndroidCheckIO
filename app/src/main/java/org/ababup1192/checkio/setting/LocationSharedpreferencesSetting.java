package org.ababup1192.checkio.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

/**
 * 位置情報関連のSharedPreferences設定
 */
public class LocationSharedpreferencesSetting {

    public static final String currentLatitude = "CURRENT_LATITUDE";
    public static final String currentLongitude = "CURRENT_LONGITUDE";

    public static final String checkpointLatitude = "CHECKPOINT_LATITUDE";
    public static final String checkpointLongitude = "CHECKPOINT_LONGITUDE";
    public static final String checkpointRadius = "CHECKPOINT_RADIUS";

    /**
     * 現在位置の保存
     *
     * @param context  Context
     * @param location 緯度経度
     */
    public static void setCurrentLocation(Context context, LatLng location) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(currentLatitude, String.valueOf(location.latitude));
        editor.putString(currentLongitude, String.valueOf(location.longitude));
        editor.apply();
    }

    /**
     * 最新位置の取得
     *
     * @param context Context
     * @return 緯度経度
     */
    public static LatLng getCurrentLocation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        String latitudeString = sharedPreferences.getString(currentLatitude, "0.0");
        String longitudeString = sharedPreferences.getString(currentLongitude, "0.0");

        return new LatLng(Double.valueOf(latitudeString), Double.valueOf(longitudeString));
    }

    /**
     * チェックポイント緯度経度の保存
     *
     * @param context  Context
     * @param location 緯度経度
     */
    public static void setCheckPointLocation(Context context, LatLng location) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(checkpointLatitude, String.valueOf(location.latitude));
        editor.putString(checkpointLongitude, String.valueOf(location.longitude));
        editor.apply();
    }

    /**
     * チェックポイント緯度経度の取得
     *
     * @param context Context
     * @return 緯度経度
     */
    public static LatLng getCheckpointLocation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        String latitudeString = sharedPreferences.getString(checkpointLatitude, "0.0");
        String longitudeString = sharedPreferences.getString(checkpointLongitude, "0.0");

        return new LatLng(Double.valueOf(latitudeString), Double.valueOf(longitudeString));
    }

    /**
     * チェックポイント半径の保存
     *
     * @param context Context
     * @param radius  半径
     */
    public static void setCheckPointRadius(Context context, float radius) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(checkpointRadius, radius);
        editor.apply();
    }

    /**
     * チェックポイント半径の取得
     *
     * @param context Context
     * @return 半径
     */
    public static float getCheckpointRadius(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);

        return sharedPreferences.getFloat(checkpointRadius, 0.0f);
    }

}
