package com.freelancewatermelon.twitterclientapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.twitter.sdk.android.core.models.Tweet;


public class TwitterClientHelper extends SQLiteOpenHelper {

    /**db version*/
    private static final int DATABASE_VERSION = 1;
    /**database name*/
    private static final String DATABASE_NAME = "home.db";
    /**ID column*/
    private static final String HOME_COL = BaseColumns._ID;
    /**tweet text*/
    private static final String UPDATE_COL = "update_text";
    /**twitter screen name*/
    private static final String USER_COL = "user_screen";
    /**time tweeted*/
    private static final String TIME_COL = "update_time";
    /**user profile image*/
    private static final String USER_IMG = "user_img";

    /**database creation string*/
    private static final String DATABASE_CREATE = "CREATE TABLE home (" + HOME_COL +
            " INTEGER NOT NULL PRIMARY KEY, " + UPDATE_COL + " TEXT, " + USER_COL +
            " TEXT, " + TIME_COL + " INTEGER, " + USER_IMG + " TEXT);";

    public TwitterClientHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS home");
        sqLiteDatabase.execSQL("VACUUM");
        onCreate(sqLiteDatabase);

    }

    /**
     * getValues retrieves the database records
     * - called from TimelineUpdater in TimelineService
     * - this is a static method that can be called without an instance of the class
     *
     * @param tweet
     * @return ContentValues result
     */
    public static ContentValues getValues(Tweet tweet) {

        //prepare ContentValues to return
        ContentValues homeValues = new ContentValues();

        //get the values
        try {
            //get each value from the table
            homeValues.put(HOME_COL, tweet.id);
            homeValues.put(UPDATE_COL, tweet.text);
            homeValues.put(USER_COL, tweet.user.screenName);
            homeValues.put(TIME_COL, tweet.createdAt);
            homeValues.put(USER_IMG, tweet.user.profileImageUrlHttps);
        }
        catch(Exception te) { Log.e("NiceDataHelper", te.getMessage()); }

        //return the values
        return homeValues;
    }
}
