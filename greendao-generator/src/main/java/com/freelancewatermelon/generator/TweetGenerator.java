package com.freelancewatermelon.generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class TweetGenerator {

    public static void main(String[] args) throws Exception {

        //place where db folder will be created inside the project folder
        Schema schema = new Schema(1, "com.freelancewatermelon.twitterclientapp.db");

        //Entity Users or table Users
        Entity user = schema.addEntity("Tweets");
        user.addIdProperty();                       //It is the primary key for uniquely identifying a row
        user.addStringProperty("tweet_username").notNull();    //Not null is SQL constrain
        user.addStringProperty("tweet_text");
        user.addStringProperty("tweet_user_img");
        user.addStringProperty("tweet_time");
        user.addStringProperty("tweet_text_image");
        //user.addDateProperty("date");

        //  ./app/src/main/java/   ----   com/codekrypt/greendao/db is the full path
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }
}
