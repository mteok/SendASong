package com.experiments.teo.youtubeexperiments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import com.activeandroid.ActiveAndroid;

/**
 * Created by teo on 4/7/15.
 */
public class MainApp extends com.activeandroid.app.Application {

    public static Context context;
    public static final String API_KEY = "&key=AIzaSyDzqF1cOcufUSF-sIGsdCOEshwNj4Cmf4c";
    public static final String API_PLAYER_KEY = "AIzaSyD6ahyhQXsjcDpRj3HB1VF_HkI5j92wGsg";
    public static final String SEARCH_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet";
    public static final String SEARCH_TYPE = "&type=video";
    public static final String MAX_RESULTS = "&maxResults=";
    public static final String PAGE_TOKEN = "&pageToken=";

    public static final String uTubeUrl = "https://www.youtube.com/watch?v=";




    public static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;
    public static final String scopes = "Scopes";
    public static final String SENDER_ID = "1327454588";

    public static String getDEVICE_ID() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static Activity mActivity;

    public static void setCurrentActivity(Activity activity) {
        mActivity = activity;
    }

    public static Activity getCurrentActivity() {
        return mActivity;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        ActiveAndroid.initialize(this);
        super.onCreate();
    }
}
