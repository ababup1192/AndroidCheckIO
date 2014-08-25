package org.ababup1192.checkio.activity.main.service.checkio;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import org.ababup1192.checkio.activity.main.MainActivity;
import org.ababup1192.checkio.util.NotificationUtil;

import java.util.List;


public class ReceiveTransitionsIntentService extends IntentService {

    public ReceiveTransitionsIntentService() {
        super("ReceiveTransitionsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationClient.hasError(intent)) {

            int errorCode = LocationClient.getErrorCode(intent);
            android.util.Log.e("ReceiveTransitionsIntentService",
                    "Location Services error: " +
                            Integer.toString(errorCode));
        } else {
            int transitionType =
                    LocationClient.getGeofenceTransition(intent);
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            // List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    NotificationUtil.notify(getApplicationContext(), mainActivityIntent
                            , "チェックイン", "チェックイン情報", "チェックインされました！");

                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    NotificationUtil.notify(getApplicationContext(), mainActivityIntent
                            , "チェックアウト", "チェックアウト情報", "チェックアウトしました！");
                    break;
                default:
                    break;

            }

        }
    }
}
