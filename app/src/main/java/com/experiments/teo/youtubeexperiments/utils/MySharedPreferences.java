package com.experiments.teo.youtubeexperiments.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.experiments.teo.youtubeexperiments.MainActivity;
import com.experiments.teo.youtubeexperiments.MainApp;

/**
 * Created by teo on 4/15/15.
 */
public class MySharedPreferences {

    private static final String PREFSTAG = "preferences";
    private static final String GCMTAG = "gcm";
    private static final String FBTOKENTAG = "tokenGcm";

    public static SharedPreferences getPrefs() {
        return MainApp.context.getSharedPreferences(PREFSTAG, Context.MODE_PRIVATE);
    }

    public static void saveGcm(String gcm) {
        getPrefs().edit().putString(GCMTAG, gcm).apply();
    }

    public static String retrieveGcm() {
        return getPrefs().getString(GCMTAG, "");
    }

    public static void saveFbToken(String fbToken) {
        getPrefs().edit().putString(FBTOKENTAG, fbToken).apply();
    }

    public static String retrieveFbToken() {
        return getPrefs().getString(FBTOKENTAG, "");
    }
}
