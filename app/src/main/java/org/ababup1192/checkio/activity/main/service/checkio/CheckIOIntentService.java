package org.ababup1192.checkio.activity.main.service.checkio;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import org.ababup1192.checkio.setting.LocationSharedpreferencesSetting;

import java.util.ArrayList;
import java.util.List;


public class CheckIOIntentService extends IntentService implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationClient.OnAddGeofencesResultListener,
        LocationClient.OnRemoveGeofencesResultListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationClient locationClient;

    public CheckIOIntentService() {
        super("CheckIOIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        locationClient = new LocationClient(this, this, this);
        locationClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        addFence();
    }

    @Override
    public void onDisconnected() {
        locationClient = null;
    }

    @Override
    public void onAddGeofencesResult(int i, String[] strings) {
        locationClient.disconnect();
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

    @Override
    public void onRemoveGeofencesByRequestIdsResult(int i, String[] strings) {
        locationClient.disconnect();
    }

    @Override
    public void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent) {
        locationClient.disconnect();
    }

    private void addFence() {
        LatLng location = LocationSharedpreferencesSetting.getCheckpointLocation(this);
        float radius = LocationSharedpreferencesSetting.getCheckpointRadius(this);

        Geofence.Builder builder = new Geofence.Builder();
        builder.setRequestId("checkpoint");
        builder.setCircularRegion(location.latitude, location.longitude, radius);
        builder.setExpirationDuration(Geofence.NEVER_EXPIRE); // 無期限
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);

        // フェンスのListを作成する。
        List<Geofence> fenceList = new ArrayList<Geofence>();
        fenceList.add(builder.build());

        // フェンス内に入った時に、指定のURIを表示するインテントを投げるようにする。
        Intent intent = new Intent(this, ReceiveTransitionsIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        locationClient.addGeofences(fenceList, pendingIntent, this);
    }
}














