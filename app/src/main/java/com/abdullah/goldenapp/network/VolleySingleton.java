package com.abdullah.goldenapp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
//    private static VolleySingleton sInstense = null;
//    private RequestQueue mRequestQueue;
//
//
//
//    private VolleySingleton() {
//        mRequestQueue = Volley.newRequestQueue(AppControler.getAppContext());
//    }
//
//    public static synchronized VolleySingleton getsInstense() {
//        if (sInstense == null) {
//            sInstense = new VolleySingleton();
//        }
//        return sInstense;
//    }
//
//    public RequestQueue getmRequestQueue() {
//        return mRequestQueue;
//    }

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    private static Context mCtx;

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }



}
