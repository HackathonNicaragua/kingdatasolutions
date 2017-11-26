package com.kingdatasolutions.sintraba.json;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.logging.L;
import com.kingdatasolutions.sintraba.utility.CustomJsonObjectRequest;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class Requestor {
    public static JSONObject requestVersionJSON(RequestQueue requestQueue, String url) {

        JSONObject params = new JSONObject();

        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params, requestFuture, requestFuture);
        //CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, params, requestFuture, requestFuture);
        //request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
        try {
            response = requestFuture.get(60000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Handler handler = new Handler(Looper.getMainLooper());
            final String error = e.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SinTrabaApp.getAppContext(), SinTrabaApp.getAppContext().getResources().getString(R.string.error_network) + "", Toast.LENGTH_LONG).show();
                }
            });
        } catch (ExecutionException e) {
            //e.notifyAll();
            final Handler handler = new Handler(Looper.getMainLooper());
            final String error = e.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SinTrabaApp.getAppContext(), SinTrabaApp.getAppContext().getResources().getString(R.string.error_network) + "", Toast.LENGTH_LONG).show();
                }
            });
        } catch (TimeoutException e) {
            Handler handler = new Handler(Looper.getMainLooper());
            final String error = e.getMessage();
            L.m("QUERY ERROR TIMEOUT " + error);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SinTrabaApp.getAppContext(), SinTrabaApp.getAppContext().getResources().getString(R.string.error_network) + "", Toast.LENGTH_LONG).show();
                }
            });
        }
        return response;
    }

    public static JSONObject requestInformationJSON(RequestQueue requestQueue, String url) {

        JSONObject params = new JSONObject();
        try {
            //params.put("server_token", SinTrabaApp.getAppContext().getString(R.string.server_token));
        } catch (Exception ex) {
        }

        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        //JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params, requestFuture, requestFuture);
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, params, requestFuture, requestFuture);
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
        try {
            response = requestFuture.get(60000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Handler handler = new Handler(Looper.getMainLooper());
            final String error = e.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SinTrabaApp.getAppContext(), SinTrabaApp.getAppContext().getResources().getString(R.string.error_network) + "", Toast.LENGTH_LONG).show();
                }
            });
        } catch (ExecutionException e) {
            //e.notifyAll();
            final Handler handler = new Handler(Looper.getMainLooper());
            final String error = e.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SinTrabaApp.getAppContext(), SinTrabaApp.getAppContext().getResources().getString(R.string.error_network) + "", Toast.LENGTH_LONG).show();
                }
            });
        } catch (TimeoutException e) {
            Handler handler = new Handler(Looper.getMainLooper());
            final String error = e.getMessage();
            L.m("QUERY ERROR TIMEOUT " + error);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SinTrabaApp.getAppContext(), SinTrabaApp.getAppContext().getResources().getString(R.string.error_network) + "", Toast.LENGTH_LONG).show();
                }
            });
        }
        return response;
    }
}
