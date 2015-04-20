package com.experiments.teo.youtubeexperiments.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;
import com.experiments.teo.youtubeexperiments.MainApp;

/**
 * Created by matteo on 18/02/15.
 */
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mDiskImageLoader;

    private VolleySingleton() {

        mRequestQueue = getRequestQueue();
        mDiskImageLoader = new ImageLoader(getRequestQueue(), new LruBitmapCache(getMaxMemory(MainApp.context) / 8));
    }

    public static synchronized VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(MainApp.context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mDiskImageLoader;
    }

    public static int getMaxMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memClassBytes = am.getMemoryClass() * 1024 * 1024;
        return memClassBytes;
    }
}
