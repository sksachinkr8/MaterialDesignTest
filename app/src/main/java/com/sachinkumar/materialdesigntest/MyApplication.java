package com.sachinkumar.materialdesigntest;

import android.app.Application;
import android.content.Context;

/**
 * Created by sachinkumar on 18/11/15.
 */
public class MyApplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES = "9htuhtcb4ymusd73d4z6jxcj";

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
