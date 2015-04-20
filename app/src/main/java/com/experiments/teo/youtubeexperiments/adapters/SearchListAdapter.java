package com.experiments.teo.youtubeexperiments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.experiments.teo.youtubeexperiments.MainApp;
import com.experiments.teo.youtubeexperiments.ParamsSingleton;
import com.experiments.teo.youtubeexperiments.R;
import com.experiments.teo.youtubeexperiments.models.VideoModel;
import com.experiments.teo.youtubeexperiments.utils.EventUI;
import com.experiments.teo.youtubeexperiments.utils.VolleySingleton;

import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by teo on 4/8/15.
 */
public class SearchListAdapter extends BaseAdapter {

    List<VideoModel> list;


    public SearchListAdapter(List<VideoModel> list) {
        this.list = list;
    }

    private class ViewHolder {
        TextView txtItem;
        TextView txtDuration;
        NetworkImageView imgItem;
        Button btnSend;

        public ViewHolder(TextView txtItem,
                          TextView txtDuration,
                          NetworkImageView imgItem,
                          Button btnSend) {
            this.txtItem = txtItem;
            this.imgItem = imgItem;
            this.txtDuration = txtDuration;
            this.btnSend = btnSend;
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(MainApp.context);
            view = inflater.inflate(R.layout.elem_search_list, viewGroup, false);
            NetworkImageView imgItem = (NetworkImageView) view.findViewById(R.id.img_search_item);
            TextView txtItem = (TextView) view.findViewById(R.id.txt_search_item_title);
            TextView txtDuration = (TextView) view.findViewById(R.id.txt_search_item_duration);
            Button btnSend = (Button) view.findViewById(R.id.btn_send_video);
            ViewHolder holder = new ViewHolder(txtItem, txtDuration, imgItem, btnSend);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        final VideoModel video = (VideoModel) getItem(i);
        View.OnClickListener launchVideo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParamsSingleton.getInstance().setSelectedVideoId(video.getYoutTubeId());
                video.setIs_played(Calendar.getInstance().getTimeInMillis());
                video.save();
                EventUI.launchVideo();
            }
        };
        View.OnClickListener goShare = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamsSingleton.getInstance().setSelectedVideoId(video.getYoutTubeId());
                video.setIs_shared(Calendar.getInstance().getTimeInMillis());
                video.save();
                EventUI.goShare();
            }
        };
        holder.imgItem.setImageUrl(video.getImg_url(), VolleySingleton.getInstance().getImageLoader());
        holder.txtItem.setText(video.getTitle());
        //Setting click listener
        holder.imgItem.setOnClickListener(launchVideo);
        holder.txtItem.setOnClickListener(launchVideo);
        holder.btnSend.setOnClickListener(goShare);
        return view;
    }
}
