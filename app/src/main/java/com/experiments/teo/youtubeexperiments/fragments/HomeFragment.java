package com.experiments.teo.youtubeexperiments.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.experiments.teo.youtubeexperiments.R;
import com.experiments.teo.youtubeexperiments.utils.EventUI;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by teo on 4/15/15.
 */
public class HomeFragment extends Fragment {


    View rootView;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.btn_guest)
    Button btnGuest;
    CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        ButterKnife.inject(this, rootView);

        return rootView;
    }


    @OnClick(R.id.btn_login)
    protected void loginClick() {
        EventUI.startFBLogin();
    }

    @OnClick(R.id.btn_guest)
    protected void guestClick() {
        EventUI.goSearch();
    }

}
