package com.experiments.teo.youtubeexperiments;

import com.experiments.teo.youtubeexperiments.models.FBUser;
import com.experiments.teo.youtubeexperiments.models.VideoModel;

import java.util.ArrayList;

/**
 * Created by teo on 4/8/15.
 */
public class ParamsSingleton {

    private static ParamsSingleton mInstance;

    private String selectedVideoId;

    private String searchingText;

    private FBUser me;

    private String nextPageResults;

    private ArrayList<FBUser> friends;

    private boolean isFbLogged;

    public static ParamsSingleton getInstance() {
        if (mInstance == null)
            mInstance = new ParamsSingleton();
        return mInstance;
    }

    private ArrayList<VideoModel> listSearchedVideos;

    public ArrayList<VideoModel> getListSearchedVideos() {
        if (listSearchedVideos == null)
            listSearchedVideos = new ArrayList<>();
        return listSearchedVideos;
    }

    public String getNextPageResults() {
        return nextPageResults;
    }

    public void setNextPageResults(String nextPageResults) {
        this.nextPageResults = nextPageResults;
    }

    public void setListSearchedVideos(ArrayList<VideoModel> listSearchedVideos) {
        this.listSearchedVideos = listSearchedVideos;
    }

    public String getSelectedVideoId() {
        return selectedVideoId;
    }

    public void setSelectedVideoId(String selectedVideoId) {
        this.selectedVideoId = selectedVideoId;
    }

    public String getSearchingText() {
        return searchingText;
    }

    public void setSearchingText(String searchingText) {
        this.searchingText = searchingText;
    }

    public ArrayList<FBUser> getFriends() {
        if (friends == null)
            friends = new ArrayList<>();
        return friends;
    }

    public void setFriends(ArrayList<FBUser> friends) {
        this.friends = friends;
    }

    public FBUser getMe() {
        return me;
    }

    public void setMe(FBUser me) {
        this.me = me;
    }

    public boolean isFbLogged() {
        return isFbLogged;
    }

    public void setFbLogged(boolean isFbLogged) {
        this.isFbLogged = isFbLogged;
    }
}
