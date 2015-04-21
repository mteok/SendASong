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

    String idFromBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(MainApp.API_PLAYER_KEY, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            idFromBundle = bundle.getString("VideoId");

        Log.d("videoIdFromBundle", String.valueOf(idFromBundle));
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            if (idFromBundle != null && idFromBundle.length() > 0)
                youTubePlayer.cueVideo(idFromBundle);
            else
                youTubePlayer.cueVideo(ParamsSingleton.getInstance().getSelectedVideoId());
        }
    }

}
