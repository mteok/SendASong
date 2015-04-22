package com.experiments.teo.youtubeexperiments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class PlayerActivity extends YouTubeFailureRecoveryActivity {

    String idFromNotification;
    String idFromSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(MainApp.API_PLAYER_KEY, this);

        idFromNotification = PushSingleton.getInstance().getNotificationMessage();

        idFromSearch = ParamsSingleton.getInstance().getSelectedVideoId();

        Log.d("videoIdFromNotification", String.valueOf(idFromNotification));
        Log.d("videoIdFromSearch", String.valueOf(idFromSearch));
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            if (idFromNotification != null && idFromNotification.length() > 0)
                youTubePlayer.cueVideo(idFromNotification);
            else if (idFromSearch != null && idFromSearch.length() > 0)
                youTubePlayer.cueVideo(ParamsSingleton.getInstance().getSelectedVideoId());
        }
    }

}
