package com.experiments.teo.youtubeexperiments.utils.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.experiments.teo.youtubeexperiments.MainApp;
import com.experiments.teo.youtubeexperiments.utils.MySharedPreferences;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by matteo on 18/02/15.
 */
public class AsyncGetGcm  extends AsyncTask<Void, Void, String> {

    private GoogleCloudMessaging gcm;
    private Context context;
    private GcmInterface gcmInterface;

    public AsyncGetGcm(GoogleCloudMessaging gcm, Context context, GcmInterface gcmInterface) {
        this.gcm = gcm;
        this.context = context;
        this.gcmInterface = gcmInterface;
    }

    @Override
    public String  doInBackground(Void... params) {
        String regid = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            regid = gcm.register(MainApp.SENDER_ID);
            Log.d("AsyncGcm","regId: "+regid);

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            //sendRegistrationIdToBackend();
            //TODO: send id to backend;

            // For this demo: we don't need to send it because the device
            // will send upstream messages to a server that echo back the
            // message using the 'from' address in the message.

            // Persist the regID - no need to register again.

        } catch (IOException ex) {
            ex.printStackTrace();
            // If there is an error, don't just keep trying to register.
            // Require the user to click a button again, or perform
            // exponential back-off.
        }
        return regid;
    }

    @Override
    public void onPostExecute(String regid) {
        MySharedPreferences.saveGcm(regid);
        gcmInterface.onIdRetrieved(regid);
    }

    public interface GcmInterface {
        public void onIdRetrieved(String id);
    }
}
