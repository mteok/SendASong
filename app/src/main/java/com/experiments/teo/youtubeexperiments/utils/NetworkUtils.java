package com.experiments.teo.youtubeexperiments.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.experiments.teo.youtubeexperiments.MainApp;
import com.experiments.teo.youtubeexperiments.ParamsSingleton;
import com.experiments.teo.youtubeexperiments.models.FBUser;
import com.experiments.teo.youtubeexperiments.models.Share;
import com.experiments.teo.youtubeexperiments.models.User;
import com.experiments.teo.youtubeexperiments.models.VideoModel;
import com.experiments.teo.youtubeexperiments.models.YouTubeSearchResult;
import com.experiments.teo.youtubeexperiments.utils.gcm.AsyncGetGcm;
import com.experiments.teo.youtubeexperiments.utils.gcm.Gps;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Objects;

/**
 * Created by teo on 4/7/15.
 */
public class NetworkUtils {

    static Gson gson = new Gson();

    private static final String hostUrl = "http://192.168.1.17:3000";
    private static final String login = "/users/login";
    private static final String shareUrl = "/push/sendPush";

    public static void login(final User user, final ResponseInterface responseInterface) {

        Gps.updateGcm(MainApp.getCurrentActivity(), new AsyncGetGcm.GcmInterface() {
            @Override
            public void onIdRetrieved(String id) {
                user.setGcm_id(id);
                Log.d("userToSend", user.toString());
                jsonRequest(hostUrl + login, user, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("response", jsonObject.toString());
                        responseInterface.onResponse(true, null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        responseInterface.onResponse(false, null);
                    }
                });
            }
        });
    }

    public static void searchVideo(String textToSearch, final ResponseInterface responseInterface) {


        final String encodedParams = Uri.encode(textToSearch);
        ParamsSingleton.getInstance().setSearchingText(encodedParams);
        final String encodedUrl = MainApp.SEARCH_URL + MainApp.API_KEY + "&q=" + encodedParams + MainApp.SEARCH_TYPE + MainApp.MAX_RESULTS+"25";
        Log.d("urlEnconded", encodedUrl);
        jsonGetRequest(encodedUrl.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("results", jsonObject.toString());
                YouTubeSearchResult results = (YouTubeSearchResult) gson.fromJson(jsonObject.toString(), YouTubeSearchResult.class);
                Toast.makeText(MainApp.context, "Trovati " + results.getPageInfo().get("totalResults").toString() + " risultati", Toast.LENGTH_LONG).show();
                ParamsSingleton.getInstance().getListSearchedVideos().clear();
                for (YouTubeSearchResult.ItemSearchResult items : results.getItem()) {
                    VideoModel videoModel = new VideoModel();
                    videoModel.setImg_url(items.getSnippet().getThumbnails().getMedium().get("url"));
                    videoModel.setTitle(items.getSnippet().getTitle());
                    videoModel.setId(items.getId().get("videoId"));
                    ParamsSingleton.getInstance().getListSearchedVideos().add(videoModel);
                }
                Log.d("NextPageToken", results.getNextPageToken());
                ParamsSingleton.getInstance().setNextPageResults(results.getNextPageToken());
                responseInterface.onResponse(true, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                responseInterface.onResponse(false, null);
            }
        });
    }

    public static void nextPageVideos(String nextPageToken, final ResponseInterface responseInterface) {
        String url = MainApp.SEARCH_URL + MainApp.API_KEY + MainApp.SEARCH_TYPE + MainApp.PAGE_TOKEN + nextPageToken + "&q=" + ParamsSingleton.getInstance().getSearchingText()+MainApp.MAX_RESULTS+"15";
        Log.d("urlNextPage", url);
        jsonGetRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                YouTubeSearchResult results = (YouTubeSearchResult) gson.fromJson(jsonObject.toString(), YouTubeSearchResult.class);
                for (YouTubeSearchResult.ItemSearchResult items : results.getItem()) {
                    VideoModel videoModel = new VideoModel();
                    videoModel.setImg_url(items.getSnippet().getThumbnails().getMedium().get("url"));
                    videoModel.setTitle(items.getSnippet().getTitle());
                    videoModel.setId(items.getId().get("videoId"));
                    ParamsSingleton.getInstance().getListSearchedVideos().add(videoModel);
                }
                ParamsSingleton.getInstance().setNextPageResults(results.getNextPageToken());
                responseInterface.onResponse(true, null);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                responseInterface.onResponse(false, null);
            }
        });
    }

    public static void shareVideo(Share share, final ResponseInterface responseInterface) {

        String urlShare = hostUrl + shareUrl;
        Log.d("sendUrl", urlShare);
        jsonRequest(urlShare, share, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("reponse", jsonObject.toString());
                responseInterface.onResponse(true, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                responseInterface.onResponse(false, null);
                Log.d("reponse", "error");
            }
        });
    }


    //Facebook resuest with sdk for request user data
    public static void facebookMeFriendsRequest(AccessToken token, GraphRequestBatch.Callback callback) {
        /**
         * User with app request
         */
        GraphRequest meRequest = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("ERROR", "me:" + String.valueOf(object));
                        if (object != null) {
                            FBUser fbUser = (FBUser) gson.fromJson(object.toString(), FBUser.class);
                            ParamsSingleton.getInstance().setMe(fbUser);
                        } else {
                            Log.d("ERROR", "me null");
                        }
                    }
                });
        /**
         * User friends with app request
         */
        GraphRequest friendsRequest = GraphRequest.newMyFriendsRequest(
                token,
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray jsonarray, GraphResponse response) {
                        Log.d("ERROR", "friends array:" + String.valueOf(jsonarray));
                        if (jsonarray != null) {
                            if (ParamsSingleton.getInstance().getFriends().size() > 0)
                                ParamsSingleton.getInstance().getFriends().clear();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                try {
                                    String jsonFriend = jsonarray.getJSONObject(i).toString();
                                    FBUser friend = (FBUser) gson.fromJson(jsonFriend, FBUser.class);
                                    ParamsSingleton.getInstance().getFriends().add(friend);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.d("ERROR", "friends array null");
                        }
                    }
                });

        Bundle b = new Bundle();
        b.putString("fields", "name,picture.width(200).height(200),id,email,gender,birthday");
        meRequest.setParameters(b);
        friendsRequest.setParameters(b);
        GraphRequestBatch rb = new GraphRequestBatch(meRequest, friendsRequest);
        rb.addCallback(callback);
        rb.executeAsync();
    }

    /**
     * Create and send a JSON request
     *
     * @param url      String, the Url to which do the post
     * @param object   Object, the object to post
     * @param listener Response.Listener, listener to manage the server's response
     * @param error    Response.ErrorListener, listener to manage a communication error
     */

    public static void jsonRequest(String url, Object object, Response.Listener<JSONObject> listener, Response.ErrorListener error) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(object));
        } catch (JSONException e) {

            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, error);
        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void jsonGetRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener error) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, listener, error);
        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public interface ResponseInterface {
        public void onResponse(boolean success, Object error);
    }

}
