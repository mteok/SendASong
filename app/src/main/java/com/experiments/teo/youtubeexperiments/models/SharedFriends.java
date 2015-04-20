package com.experiments.teo.youtubeexperiments.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by teo on 4/20/15.
 */
@Table(name = "shared_friends")
public class SharedFriends extends Model {

    @Column(name = "video_id")
    private String video_id;

    @Column(name = "friend_id")
    private String friend_id;

    @Column(name = "shared_timestamp")
    private Long shared_timestamp;

    public SharedFriends() {
        super();
    }

    public SharedFriends(String video_id, String friend_id, Long shared_timestamp) {
        this.video_id = video_id;
        this.friend_id = friend_id;
        this.shared_timestamp = shared_timestamp;
        this.save();
    }
}
