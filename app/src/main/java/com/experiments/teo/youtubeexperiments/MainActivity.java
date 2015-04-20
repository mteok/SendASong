package com.experiments.teo.youtubeexperiments;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.experiments.teo.youtubeexperiments.fragments.FragmentFriendList;
import com.experiments.teo.youtubeexperiments.fragments.HomeFragment;
import com.experiments.teo.youtubeexperiments.fragments.ProfileFragment;
import com.experiments.teo.youtubeexperiments.fragments.SearchFragment;
import com.experiments.teo.youtubeexperiments.models.User;
import com.experiments.teo.youtubeexperiments.utils.EventUI;
import com.experiments.teo.youtubeexperiments.utils.MySharedPreferences;
import com.experiments.teo.youtubeexperiments.utils.NetworkUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class MainActivity extends FragmentActivity {

    CallbackManager callbackManager;

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.setCurrentActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        EventUI.goHome();
    }

    public void onEvent(EventUI.Search ev) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
    }

    public void onEvent(EventUI.Home ev) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void onEvent(EventUI.Profile ev) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }

    public void onEvent(EventUI.VideoPage ev) {
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void onEvent(EventUI.Share ev) {
        if (ParamsSingleton.getInstance().isFbLogged())
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentFriendList()).commit();
        else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_TEXT, MainApp.uTubeUrl + ParamsSingleton.getInstance().getSelectedVideoId());
            startActivity(intent);
        }
    }

    public void onEvent(EventUI.FBLogin ev) {
        facebookLogin();
    }

    public void facebookLogin() {
        FacebookSdk.sdkInitialize(MainApp.context);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, onLoginListener);
        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("user_friends");
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);

    }

    FacebookCallback<LoginResult> onLoginListener = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            MySharedPreferences.saveFbToken(loginResult.getAccessToken().getToken());
            NetworkUtils.login(new User(loginResult.getAccessToken().getUserId(), null), loginResponse);
            NetworkUtils.facebookMeFriendsRequest(loginResult.getAccessToken(), onMeFriendsResult);
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
            e.printStackTrace();
        }
    };

    private NetworkUtils.ResponseInterface loginResponse = new NetworkUtils.ResponseInterface() {
        @Override
        public void onResponse(boolean success, Object error) {
            if (success) {
                EventUI.goSearch();
            }
        }
    };

    GraphRequestBatch.Callback onMeFriendsResult = new GraphRequestBatch.Callback() {
        @Override
        public void onBatchCompleted(GraphRequestBatch graphRequests) {
            ParamsSingleton.getInstance().setFbLogged(true);
            EventUI.goSearch();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                EventUI.goProfile();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainApp.setCurrentActivity(null);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
