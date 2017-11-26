package com.kingdatasolutions.sintraba.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.kingdatasolutions.sintraba.callback.GeneralLoadedListener;
import com.kingdatasolutions.sintraba.network.VolleySingleton;
import com.kingdatasolutions.sintraba.utility.InformationUtils;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class TaskLoadGeneral extends AsyncTask<Void, Void, Boolean> {
    private GeneralLoadedListener mComponent;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;

    public TaskLoadGeneral(GeneralLoadedListener component) {
        this.mComponent = component;
        mVolleySingleton = VolleySingleton.getsInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        InformationUtils.loadGeneral(mRequestQueue);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean isSuccessful) {
        if (mComponent != null) {
            mComponent.onGeneralLoaded(isSuccessful);
        }
    }
}
