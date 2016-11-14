package com.freelancewatermelon.twitterclientapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freelancewatermelon.twitterclientapp.network.VolleySingleton;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit2.Response;

public class TweetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Tweet> tweets;
    private NetworkImageView userImage;
    private TextView userName;
    private TextView updateTime;
    private TextView updateText;


    private class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View view) {
            super(view);
            userImage = (NetworkImageView) view.findViewById(R.id.userImg);
            userName = (TextView) view.findViewById(R.id.userScreen);
            updateTime = (TextView) view.findViewById(R.id.updateTime);
            updateText = (TextView) view.findViewById(R.id.updateText);

        }


    }

    public TweetRecyclerViewAdapter(Context context, Response<List<Tweet>> tweets) {
        this.tweets = tweets.body();
        this.context = context;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("UCITANO", "" + tweets.get(position).user.screenName);
        holder.setIsRecyclable(false);



        ImageLoader mImageLoader = VolleySingleton.getsInstance(context).getImageLoader();
        userImage.setImageUrl(tweets.get(position).user.profileImageUrlHttps, mImageLoader);
        userName.setText(tweets.get(position).user.screenName);
        updateTime.setText(tweets.get(position).createdAt);
        updateText.setText(tweets.get(position).text);


    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
}
