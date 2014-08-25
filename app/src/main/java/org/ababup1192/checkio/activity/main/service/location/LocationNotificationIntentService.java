package org.ababup1192.checkio.activity.main.service.location;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

import org.ababup1192.checkio.activity.maps.MapsActivity;
import org.ababup1192.checkio.setting.LocationSharedpreferencesSetting;
import org.ababup1192.checkio.util.LocationUtil;
import org.ababup1192.checkio.util.NotificationUtil;

public class LocationNotificationIntentService extends IntentService {
    private final String TAG = LocationNotificationIntentService.class.getSimpleName();

    public LocationNotificationIntentService() {
        super("LocationNotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LatLng location = LocationSharedpreferencesSetting.getCurrentLocation(getApplicationContext());

        // 位置情報が取得できて入ればステータスバーへ通知
        if (LocationUtil.isDefinedLocation(location)) {
            Intent mapsIntent = MapsActivity.createIntent(getApplicationContext(), location);
            NotificationUtil.notify(getApplicationContext(), mapsIntent
                    , "位置情報通知", "最新位置情報をマップで確認", "緯度:" + location.latitude + ", 緯度:" + location.longitude);
        }
    }
}