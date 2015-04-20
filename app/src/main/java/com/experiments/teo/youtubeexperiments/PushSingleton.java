package com.experiments.teo.youtubeexperiments;

/**
 * Created by teo on 4/20/15.
 */
public class PushSingleton {

    private static PushSingleton mInstance;

    public static PushSingleton getInstance() {
        if (mInstance == null)
            mInstance = new PushSingleton();
        return mInstance;
    }

    private MainActivity activity;

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }
}
