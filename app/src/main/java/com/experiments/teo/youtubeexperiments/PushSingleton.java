package com.experiments.teo.youtubeexperiments;

/**
 * Created by teo on 4/20/15.
 */
public class PushSingleton {

    private static PushSingleton mInstance;

    private static String notificationMessage;

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

    public static String getNotificationMessage() {
        return notificationMessage;
    }

    public static void setNotificationMessage(String notificationMessage) {
        PushSingleton.notificationMessage = notificationMessage;
    }
}
