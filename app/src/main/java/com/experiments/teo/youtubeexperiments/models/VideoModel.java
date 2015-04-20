package com.experiments.teo.youtubeexperiments.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teo on 4/8/15.
 */
@Table(name = "Videos")
public class VideoModel extends Model {
    @Column(name = "img_url")
    private String img_url;
    @Column(name = "title")
    private String title;
    @Column(name = "youtube_id")
    private String youtubeid;
    @Column(name = "is_played")
    private Long is_played;
    @Column(name = "is_shared")
    private Long is_shared;


    public VideoModel() {
        super();
    }

    public VideoModel(String img_url, String title, String youtubeid) {
        super();
        this.setId(youtubeid);
        this.setTitle(title);
        this.setImg_url(img_url);
    }

    public static List<VideoModel> getPlayedVideos() {
        return new Select().from(VideoModel.class).where("is_played <> NULL ").orderBy("is_played ASC").execute();
    }

    public static List<VideoModel> getSharedVideos() {
        return new Select().from(VideoModel.class).where("is_shared <> NULL ").orderBy("is_shared ASC").execute();
    }

    public Long getIs_played() {
        return is_played;
    }

    public void setIs_played(Long is_played) {
        this.is_played = is_played;
    }

    public Long getIs_shared() {
        return is_shared;
    }

    public void setIs_shared(Long is_shared) {
        this.is_shared = is_shared;
    }

    public String getYoutTubeId() {
        return youtubeid;
    }

    public void setId(String id) {
        this.youtubeid = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
