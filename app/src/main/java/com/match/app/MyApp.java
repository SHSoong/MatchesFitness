package com.match.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.match.app.common.User;
import com.match.app.ui.login.LoginActivity;

import java.util.List;
import java.util.Stack;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static MyApp app;

    public static Context getContext() {
        return app;
    }

    private static Stack<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }

    public static void logout(Context context) {
        if (activities != null && !activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
        User.getInstance().setLogin(false);
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
