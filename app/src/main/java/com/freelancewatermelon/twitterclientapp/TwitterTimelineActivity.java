package com.freelancewatermelon.twitterclientapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;


import java.io.IOException;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import retrofit2.Response;


public class TwitterTimelineActivity extends AppCompatActivity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "jJC29MJuuCfJKFVrvn0sxegxs";
    private static final String TWITTER_SECRET = "PpCm9xQvvv00xCn3kvTmKz6KoHtYU01RohhYPFy8XW1TlbF6l0";

    private static final String SEARCH_QUERY = "Almounir";
    private static final String SEARCH_RESULT_TYPE = "recent";
    private static final int SEARCH_COUNT = 20;
    private long maxId;
    private Response<List<Tweet>> callTweetsList;
    private TweetRecyclerViewAdapter adapter;
    private TweetTimelineListAdapter timelineListAdapter;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));


        setContentView(R.layout.timeline);

        recyclerView = (RecyclerView) findViewById(R.id.homeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (twitterSession != null) {
            Log.d("PROVERA", twitterSession.getUserName());

            new RetrieveHomeTimeline().execute();


        }


    }

    private class RetrieveHomeTimeline extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();
            try {
                callTweetsList = service.homeTimeline(null, null, null, null, null, null, null).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void a) {
            // TODO: check this.exception
            // TODO: do something with the feed
            Log.d("UCITANO", "" + callTweetsList.body().size());
            recyclerView.setAdapter(new TweetRecyclerViewAdapter(getApplicationContext(), callTweetsList));
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                TwitterCore.getInstance().logOut();
                startActivity(new Intent(getApplicationContext(), LoginWithTwitterActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
