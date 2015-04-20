package com.experiments.teo.youtubeexperiments.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.experiments.teo.youtubeexperiments.MainApp;
import com.experiments.teo.youtubeexperiments.R;
import com.experiments.teo.youtubeexperiments.models.FBUser;
import com.experiments.teo.youtubeexperiments.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by teo on 4/17/15.
 */
public class FriendListAdapter extends BaseAdapter {

    ArrayList<FBUser> friendList;

    public FriendListAdapter(ArrayList<FBUser> friendList) {
        this.friendList = friendList;
    }

    private class ViewHolder {
        NetworkImageView imgFriend;
        TextView txtName;
        CheckBox chkFriend;

        public ViewHolder(TextView txtName, NetworkImageView imgFriend, CheckBox chkFriend) {
            this.txtName = txtName;
            this.imgFriend = imgFriend;
            this.chkFriend = chkFriend;
        }
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public FBUser getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(MainApp.context);
            convertView = inflater.inflate(R.layout.friend_item, parent, false);
            NetworkImageView imgFriend = (NetworkImageView) convertView.findViewById(R.id.img_friend);
            TextView txtName = (TextView) convertView.findViewById(R.id.txt_friend_name);
            CheckBox chkFriend = (CheckBox)convertView.findViewById(R.id.chk_friend);
            ViewHolder holder = new ViewHolder(txtName, imgFriend,chkFriend);
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.imgFriend.setImageUrl(getItem(position).getPicture().getData().get("url"), VolleySingleton.getInstance().getImageLoader());
        holder.txtName.setText(getItem(position).getName());
        holder.chkFriend.setChecked(getItem(position).isSelected());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).setSelected(!getItem(position).isSelected());
                Log.d("selected", String.valueOf(getItem(position).isSelected()));
                holder.chkFriend.setChecked(getItem(position).isSelected());
            }
        });
        return convertView;
    }
}
