package com.match.app;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static MyApp app;

    public static Context getContext() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
