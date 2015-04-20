package com.experiments.teo.youtubeexperiments.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.experiments.teo.youtubeexperiments.R;

import butterknife.ButterKnife;

/**
 * Created by teo on 4/8/15.
 */
public class VideoPlayerFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            inflater.inflate(R.layout.fragment_player, container, false);
            ButterKnife.inject(this, rootView);
        }

        return rootView;
    }
}
