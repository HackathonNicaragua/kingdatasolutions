package com.kingdatasolutions.sintraba;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kingdatasolutions.sintraba.data.SinTrabaDBHelper;

import java.util.Locale;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class SinTrabaApp extends Application {

    public static String sDefSystemLanguage;
    private static SinTrabaApp sInstance;
    private static SinTrabaDBHelper mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sDefSystemLanguage = Locale.getDefault().getLanguage();
        sInstance = this;
        Fresco.initialize(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        sDefSystemLanguage = newConfig.locale.getLanguage();
    }

    public static SinTrabaApp getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public synchronized static SinTrabaDBHelper getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new SinTrabaDBHelper(getAppContext());
        }
        return mDatabase;
    }
}
