package com.kingdatasolutions.sintraba.json;

import com.kingdatasolutions.sintraba.utility.UrlEndPoints;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class EndPoints {

    public static String getRequestUrlGeneral() {

        return UrlEndPoints.URL_SERVER
                + UrlEndPoints.URL_MODULE_GENERAL;
    }

    public static String getRequestUrlInformation() {

        return UrlEndPoints.URL_SERVER
                + UrlEndPoints.URL_MODULE_INFORMATION;
    }

    public static String getRequestUrlMapImage(double latitude, double longitude, int zoom, int widht, int height) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=" + zoom + "&markers=color:purple%7C" + latitude + "," +longitude + "&size=" + widht + "x" + height;
    }

    public static String getRequestUrlDestinationDetailMapImage(double latitude, double longitude) {
        return getRequestUrlMapImage(latitude, longitude, 9, 500, 700);
    }
}
