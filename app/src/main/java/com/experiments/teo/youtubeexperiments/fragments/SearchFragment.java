package com.experiments.teo.youtubeexperiments.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.experiments.teo.youtubeexperiments.ParamsSingleton;
import com.experiments.teo.youtubeexperiments.R;
import com.experiments.teo.youtubeexperiments.adapters.SearchListAdapter;
import com.experiments.teo.youtubeexperiments.utils.EventUI;
import com.experiments.teo.youtubeexperiments.utils.NetworkUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by teo on 4/7/15.
 */
public class SearchFragment extends Fragment {

    View rootView;
    @InjectView(R.id.btn_search)
    Button btnSearch;
    @InjectView(R.id.edit_search)
    EditText editSearch;
    @InjectView(R.id.list_searched_item)
    ListView listViewSearch;

    String searchingText;
    SearchListAdapter adapter;
    boolean resultsDisplayed = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_search, container, false);
        }
        ButterKnife.inject(this, rootView);
        adapter = new SearchListAdapter(ParamsSingleton.getInstance().getListSearchedVideos());
        listViewSearch.setAdapter(adapter);
        listViewSearch.setOnScrollListener(scrollListener);
        getActivity().getActionBar().setTitle(getString(R.string.title_search));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    Log.i("", "here scrolling is finished");
                    if (resultsDisplayed) {
                        NetworkUtils.nextPageVideos(ParamsSingleton.getInstance().getNextPageResults(), response);
                        resultsDisplayed = false;
                    }
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    Log.i("", "here scrolling is finished");
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    Log.i("", "its scrolling....");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };


    @OnClick(R.id.btn_search)
    protected void search() {
        searchingText = editSearch.getText().toString();
        NetworkUtils.searchVideo(searchingText, response);
    }



    private NetworkUtils.ResponseInterface response = new NetworkUtils.ResponseInterface() {

        @Override
        public void onResponse(boolean success, Object error) {
            if (success && !resultsDisplayed) {
                adapter.notifyDataSetChanged();
                resultsDisplayed = true;
            }
        }
    };
}
