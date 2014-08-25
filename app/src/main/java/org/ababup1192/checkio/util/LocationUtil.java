package org.ababup1192.checkio.util;

import com.google.android.gms.maps.model.LatLng;

public class LocationUtil {

    public static boolean isEmptyLocation(LatLng location) {
        return location.latitude == 0.0 && location.longitude == 0.0;
    }

    public static boolean isDefinedLocation(LatLng location) {
        return !isEmptyLocation(location);
    }

}
