package org.ababup1192.checkio.activity.maps.checkpoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ababup1192.checkio.R;
import org.ababup1192.checkio.setting.LocationSharedpreferencesSetting;
import org.ababup1192.checkio.util.LocationUtil;

public class CheckpointMapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng location) {
                map.clear();

                float radius = LocationSharedpreferencesSetting.getCheckpointRadius(getApplicationContext());

                addMaker(location);
                addCircle(location, radius);

                // チェックポイントの変更
                LocationSharedpreferencesSetting.setCheckPointLocation(getApplicationContext(), location);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, CheckpointMapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
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
        LatLng initLocation = getInitLocation();
        // 何となく初期位置は東京ドーム
        moveCamera(initLocation);

        // チェックポイントが既に存在している場合は、マーカーセット
        LatLng checkpointLocation = LocationSharedpreferencesSetting.getCheckpointLocation(this);
        if (LocationUtil.isDefinedLocation(checkpointLocation)) {
            float radius = LocationSharedpreferencesSetting.getCheckpointRadius(getApplicationContext());

            addMaker(checkpointLocation);
            addCircle(checkpointLocation, radius);
        }
    }

    /**
     * 初期位置を取得する。
     * 優先度は、チェックポイント, 最新位置, 東京ドームの順
     *
     * @return 位置情報
     */
    private LatLng getInitLocation() {
        LatLng checkpointLocation = LocationSharedpreferencesSetting.getCheckpointLocation(this);
        LatLng currentLocation = LocationSharedpreferencesSetting.getCurrentLocation(this);
        if (LocationUtil.isDefinedLocation(checkpointLocation)) {
            return checkpointLocation;
        } else if (LocationUtil.isDefinedLocation(currentLocation)) {
            return currentLocation;
        } else {
            // 初期位置は何となく東京ドーム
            return new LatLng(35.7056396, 139.7518913);
        }
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

    private void addCircle(LatLng target, float radius) {

        CircleOptions circleOptions = new CircleOptions()
                .center(target)
                .radius(radius);
        map.addCircle(circleOptions);
    }

}
