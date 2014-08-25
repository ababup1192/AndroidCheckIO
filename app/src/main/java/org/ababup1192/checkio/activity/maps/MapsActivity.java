package org.ababup1192.checkio.activity.maps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ababup1192.checkio.R;
import org.ababup1192.checkio.setting.LocationIntentSetting;

public class MapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * 位置情報を付与したIntentを生成
     *
     * @param context  Context
     * @param location 位置情報
     * @return Intent
     */
    public static Intent createIntent(Context context, LatLng location) {
        Intent mapsIntent = new Intent(context, MapsActivity.class);
        mapsIntent.putExtra(LocationIntentSetting.latitude, location.latitude);
        mapsIntent.putExtra(LocationIntentSetting.longitude, location.longitude);
        return mapsIntent;
    }

    /**
     * 位置情報をIntentから取得
     *
     * @return 位置情報
     */
    private LatLng getLocationExtra() {
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra(LocationIntentSetting.latitude, 0.0);
        double longitude = intent.getDoubleExtra(LocationIntentSetting.longitude, 0.0);
        return new LatLng(latitude, longitude);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #map} is not null.
     */
    private void setUpMap() {
        LatLng target = getLocationExtra();             // 初期位置

        moveCamera(target);
        addMaker(target);
    }

    private void moveCamera(LatLng target) {
        float zoom = 18.0f;                                 //ズームレベル
        float tilt = 90.0f;                                 //チルトアングル
        float bearing = 0.0f;                               //向き

        CameraPosition pos = new CameraPosition(target, zoom, tilt, bearing);
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(pos);

        map.moveCamera(camera);
    }

    private void addMaker(LatLng target) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(target);

        map.addMarker(marker);
    }

}
