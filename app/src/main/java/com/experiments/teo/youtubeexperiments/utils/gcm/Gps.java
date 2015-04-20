package com.experiments.teo.youtubeexperiments.utils.gcm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.experiments.teo.youtubeexperiments.MainApp;
import com.experiments.teo.youtubeexperiments.utils.MySharedPreferences;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by matteo on 18/02/15.
 */
public class Gps {
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static void updateGcm(Context context, AsyncGetGcm.GcmInterface gcmInterface) {

        if (checkPlayServices((Activity) context)) {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

            String regid = MySharedPreferences.retrieveGcm();

            Log.d("SharedId", regid);
            if (regid.isEmpty()) {
                AsyncGetGcm getGcm = new AsyncGetGcm(gcm, context, gcmInterface);
                getGcm.execute();
            } else
                gcmInterface.onIdRetrieved(regid);
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public static boolean checkPlayServices(Activity activity) {

        try {
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Log.i("GPS", "This device is not supported.");
                    activity.finish();
                    //TODO: show dialog
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    public static void fetchToken(final String mEmail, final Activity activity, final AsyncGetGcm.GcmInterface gcmInterface) {


        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... objects) {
                try {
                    return GoogleAuthUtil.getToken(activity, mEmail, MainApp.scopes);

                } catch (UserRecoverableAuthException userRecoverableException) {

                    if (userRecoverableException instanceof GooglePlayServicesAvailabilityException) {
                        // The Google Play services APK is old, disabled, or not present.
                        // Show a dialog created by Google Play services that allows
                        // the user to update the APK
                        int statusCode = ((GooglePlayServicesAvailabilityException) userRecoverableException)
                                .getConnectionStatusCode();
                        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                                activity,
                                MainApp.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                        dialog.show();
                    } else if (userRecoverableException instanceof UserRecoverableAuthException) {
                        // Unable to authenticate, such as when the user has not yet granted
                        // the app access to the account, but the user can fix this.
                        // Forward the user to an activity in Google Play services.
                        Intent intent = ((UserRecoverableAuthException) userRecoverableException).getIntent();
                        activity.startActivityForResult(intent,
                                MainApp.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    }

                    return null;
                } catch (GoogleAuthException fatalException) {
                    // Some other type of unrecoverable exception has occurred.
                    // Report and log the error as appropriate for your app.
                    fatalException.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String token) {
                super.onPostExecute(token);
                if (token != null)
                    gcmInterface.onIdRetrieved(token);
            }
        };

        asyncTask.execute();


    }
}

