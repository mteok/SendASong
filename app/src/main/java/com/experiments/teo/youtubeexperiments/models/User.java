package com.experiments.teo.youtubeexperiments.models;

import com.google.gson.annotations.Expose;

/**
 * Created by teo on 4/15/15.
 */
public class User {

    @Expose
    private String user_id;
    @Expose
    private String gcm_id;
    @Expose
    private String user_name;

    public User(String user_id, String gcm_id,String user_name) {
        this.user_id = user_id;
        this.gcm_id = gcm_id;
        this.user_name=user_name;
    }

    @Override
    public String toString() {
        return "user_id: " + getUser_id() + ", gcm_id:" + getGcm_id();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGcm_id() {
        return gcm_id;
    }

    public void setGcm_id(String gcm_id) {
        this.gcm_id = gcm_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
