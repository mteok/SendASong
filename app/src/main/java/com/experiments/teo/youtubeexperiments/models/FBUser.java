package com.experiments.teo.youtubeexperiments.models;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

/**
 * Created by teo on 4/10/15.
 */
public class FBUser {

    @Expose
    private String name;
    @Expose
    private String id;
    @Expose
    private UserImage picture;
    @Expose
    private String email;
    @Expose
    private String gender;
    @Expose
    private String birthday;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "Name:" + name + ",id:" + id + ",email:" + email+",gender:"+gender+",birthday:"+birthday;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserImage getPicture() {
        return picture;
    }

    public void setPicture(UserImage picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public class UserImage {
        @Expose
        private HashMap<String, String> data;

        public HashMap<String, String> getData() {
            return data;
        }

        public void setData(HashMap<String, String> data) {
            this.data = data;
        }
    }
}
