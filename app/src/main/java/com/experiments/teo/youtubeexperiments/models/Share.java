package com.experiments.teo.youtubeexperiments.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by teo on 4/17/15.
 */
public class Share {

    @Expose
    private ArrayList<String> user_ids;

    @Expose
    private String video_id;

    public ArrayList<String> getUser_ids() {
        if (user_ids == null)
            user_ids = new ArrayList<>();
        return user_ids;
    }

    public void setUser_ids(ArrayList<String> user_ids) {
        this.user_ids = user_ids;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
