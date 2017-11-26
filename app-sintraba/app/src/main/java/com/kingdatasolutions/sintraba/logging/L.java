package com.kingdatasolutions.sintraba.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class L {
    public static void m(String message) {
        Log.d("NESTORBONILLA", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void T(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
