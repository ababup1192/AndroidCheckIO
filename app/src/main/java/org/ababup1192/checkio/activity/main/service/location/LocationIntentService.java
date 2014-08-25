package org.ababup1192.checkio.activity.main.service.location;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

import org.ababup1192.checkio.setting.LocationSharedpreferencesSetting;

import java.util.concurrent.TimeUnit;

public class LocationIntentService extends IntentService implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private final String TAG = LocationIntentService.class.getSimpleName();

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private LocationListener locationListener;

    public LocationIntentService() {
        super("LocationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        locationClient = new LocationClient(this, this, this);

        locationRequest = LocationRequest.create();
        // 60 秒おきに位置情報を取得する
        locationRequest.setInterval(TimeUnit.MINUTES.toMillis(1));
        // 精度優先
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // locationRequest.setFastestInterval(TimeUnit.SECONDS.toMillis(90));
        locationRequest.setNumUpdates(3);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // SharedPreferencesの現在位置情報に保存
                LocationSharedpreferencesSetting.setCurrentLocation(getApplicationContext(),
                        new LatLng(location.getLatitude(), location.getLongitude()));
            }
        };

        locationClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        locationClient.requestLocationUpdates(locationRequest, locationListener);
    }

    @Override
    public void onDisconnected() {
        locationClient = null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        (Activity) getApplicationContext(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        }
    }

}