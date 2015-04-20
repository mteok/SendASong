package com.experiments.teo.youtubeexperiments.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.experiments.teo.youtubeexperiments.ParamsSingleton;
import com.experiments.teo.youtubeexperiments.R;
import com.experiments.teo.youtubeexperiments.adapters.SearchListAdapter;
import com.experiments.teo.youtubeexperiments.models.FBUser;
import com.experiments.teo.youtubeexperiments.models.VideoModel;
import com.experiments.teo.youtubeexperiments.utils.VolleySingleton;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by teo on 4/20/15.
 */
public class ProfileFragment extends Fragment {

    View rootView;
    @InjectView(R.id.profile_picture)
    NetworkImageView imgProfile;
    @InjectView(R.id.profile_name)
    TextView txtName;
    @InjectView(R.id.listview_video_played)
    ListView listvideosPlayed;
    @InjectView(R.id.listview_video_shared)
    ListView listvideosShared;
    @InjectView(R.id.btn_played)
    Button btnPlayed;
    @InjectView(R.id.btn_shared)
    Button btnShared;

    TabHost tabHost;
    SearchListAdapter adapterPlayed, adapterShared;
    List<VideoModel> playedVideo;
    List<VideoModel> sharedVideo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        }
        ButterKnife.inject(this, rootView);
        playedVideo = VideoModel.getPlayedVideos();
        sharedVideo = VideoModel.getSharedVideos();
        adapterPlayed = new SearchListAdapter(playedVideo);
        adapterShared = new SearchListAdapter(sharedVideo);
        listvideosPlayed.setAdapter(adapterPlayed);
        listvideosShared.setAdapter(adapterShared);
        FBUser fbUser = ParamsSingleton.getInstance().getMe();
        imgProfile.setImageUrl(fbUser.getPicture().getData().get("url"), VolleySingleton.getInstance().getImageLoader());
        txtName.setText(fbUser.getName());
        return rootView;
    }

    @OnClick(R.id.btn_played)
    protected void btnPlayedClick() {
        listvideosPlayed.setVisibility(View.VISIBLE);
        listvideosShared.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_shared)
    protected void btnSharedClick() {
        listvideosPlayed.setVisibility(View.GONE);
        listvideosShared.setVisibility(View.VISIBLE);
    }


}
