package com.experiments.teo.youtubeexperiments.utils.gcm;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.experiments.teo.youtubeexperiments.MainActivity;
import com.experiments.teo.youtubeexperiments.MainApp;
import com.experiments.teo.youtubeexperiments.PlayerActivity;
import com.experiments.teo.youtubeexperiments.PushSingleton;
import com.experiments.teo.youtubeexperiments.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;

/**
 * Created by matteo on 18/02/15.
 */
public class PushIntentService extends IntentService {
    private static final String TAG = PushIntentService.class.getSimpleName();

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;


    public PushIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // pushSingleton = PushSingleton.getInstance(this);
    }


    @Override
    protected void onHandleIntent(Intent intent) {


        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        Log.d(TAG, "new push message!!!");
        Log.d(TAG, messageType);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                String video_id = intent.getStringExtra("video_id");
                String name = intent.getStringExtra("user_name");
                if (PushSingleton.getInstance().getActivity() != null) {
                    Log.d("message", video_id);
                } else {
                    Log.d("push", "success");
                    PushSingleton.getInstance().setNotificationMessage(video_id);
                    notification(video_id, name);
                }

            } else if (messageType == null) {

            }

        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        PushBroadcastReceiver.completeWakefulIntent(intent);
    }

    private static void notification(String videoId, String name) {
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) MainApp.context.getSystemService(Context.NOTIFICATION_SERVICE);
        String message = name + " " + MainApp.context.getString(R.string.notification_msg);
        Notification notification = new Notification(icon, message, when);

        String title = MainApp.context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(MainApp.context, PlayerActivity.class);

        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(MainApp.context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(MainApp.context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }
    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.

/*
    private static void generateCheckOutNotification() {

        notification(MainApp.context.getResources().getString(R.string.check_out_notification));
    }


    private static void generateCheckInNotification(boolean success) {

        String message = "";
        if (success)
            message = MainApp.context.getResources().getString(R.string.checkin_notification_success);
        else
            message = MainApp.context.getResources().getString(R.string.checkin_notification_success);

        notification(message);
    }

    private static void generateBNBNotification(boolean success, String serviceName) {

        String message = "";
        if (success)
            message = serviceName + " " + MainApp.context.getResources().getString(R.string.bnb_notification_success);
        else
            message = serviceName + " " + MainApp.context.getResources().getString(R.string.bnb_notification_success);

        notification(message);
    }




    public boolean isRunning() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("current task :", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClass().getSimpleName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equalsIgnoreCase("com.askiitech.cierge")) {
            //Activity in foreground, broadcast intent
            return true;
        } else {
            //Activity Not Running
            return false;
        }
    }
*/
}
