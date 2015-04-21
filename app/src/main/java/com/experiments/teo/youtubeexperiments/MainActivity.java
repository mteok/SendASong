package com.experiments.teo.youtubeexperiments;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
import com.scopely.fontain.Fontain;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class MainActivity extends FragmentActivity {

    CallbackManager callbackManager;

    @Override
    protected void onResume() {
        super.onResume();
        PushSingleton.getInstance().setActivity(this);
        MainApp.setCurrentActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        Fontain.init(this, "POLAR");

        if (ParamsSingleton.getInstance().isFbLogged())
            EventUI.goSearch();
        else
            EventUI.goHome();
    }

    public void onEvent(EventUI.Search ev) {
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, new SearchFragment(), SearchFragment.class.getName()).commit();
    }

    public void onEvent(EventUI.Home ev) {
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, new HomeFragment(), HomeFragment.class.getName()).commit();
    }

    public void onEvent(EventUI.Profile ev) {
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(ProfileFragment.class.getName()).replace(R.id.fragment_container, new ProfileFragment(), ProfileFragment.class.getName()).commit();
    }

    public void onEvent(EventUI.VideoPage ev) {
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void onEvent(EventUI.Share ev) {
        if (ParamsSingleton.getInstance().isFbLogged())
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(FragmentFriendList.class.getName()).replace(R.id.fragment_container, new FragmentFriendList(), FragmentFriendList.class.getName()).commit();
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
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (ParamsSingleton.getInstance().isFbLogged()) {
            invalidateOptionsMenu();
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_profile:
                EventUI.goProfile();
                break;
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                ParamsSingleton.getInstance().setFbLogged(false);
                EventUI.goHome();
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
        PushSingleton.getInstance().setActivity(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
