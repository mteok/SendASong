package com.experiments.teo.youtubeexperiments.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.experiments.teo.youtubeexperiments.ParamsSingleton;
import com.experiments.teo.youtubeexperiments.R;
import com.experiments.teo.youtubeexperiments.adapters.FriendListAdapter;
import com.experiments.teo.youtubeexperiments.models.FBUser;
import com.experiments.teo.youtubeexperiments.models.Share;
import com.experiments.teo.youtubeexperiments.models.SharedFriends;
import com.experiments.teo.youtubeexperiments.utils.NetworkUtils;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by teo on 4/17/15.
 */
public class FragmentFriendList extends Fragment {

    View rootView;
    @InjectView(R.id.list_friends)
    ListView friendList;
    @InjectView(R.id.button_share)
    Button share;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);
        }

        ButterKnife.inject(this, rootView);
        FriendListAdapter adapter = new FriendListAdapter(ParamsSingleton.getInstance().getFriends());
        friendList.setAdapter(adapter);
        return rootView;
    }

    @OnClick(R.id.button_share)
    protected void send() {
        Share objToSend = new Share();
        for (FBUser f : ParamsSingleton.getInstance().getFriends()) {
            if (f.isSelected()) {
                objToSend.getUser_ids().add(f.getId());
                Log.d("idFriend", f.getId());
                new SharedFriends(ParamsSingleton.getInstance().getSelectedVideoId(), f.getId(), Calendar.getInstance().getTimeInMillis());
            }
        }
        objToSend.setVideo_id(ParamsSingleton.getInstance().getSelectedVideoId());
        NetworkUtils.shareVideo(objToSend, shareListener);
    }

    private NetworkUtils.ResponseInterface shareListener = new NetworkUtils.ResponseInterface() {
        @Override
        public void onResponse(boolean success, Object error) {

        }
    };
}
