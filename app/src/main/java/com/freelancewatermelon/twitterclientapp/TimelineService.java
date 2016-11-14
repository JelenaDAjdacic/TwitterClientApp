package com.freelancewatermelon.twitterclientapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.io.IOException;
import java.util.List;

public class TimelineService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * TimelineUpdater class implements the runnable interface
     */
    class TimelineUpdater implements Runnable {
        @Override
        public void run() {
            //check for updates - assume none
            boolean statusChanges = false;
            try {
                TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                //fetch timeline
                if (twitterSession != null) {

                    try {
                        final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();
                        List<Tweet> tweetsList = service.homeTimeline(null, null, null, null, null, null, null).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            } catch (Exception te) {
                Log.e("SERVISE", "Exception: " + te);
            }
        }
        //fetch updates

    }
}
