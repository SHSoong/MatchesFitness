package com.match.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.match.app.common.User;
import com.match.app.db.DBHelper;
import com.match.app.db.TbAccount;
import com.match.app.ui.home.activity.MainActivity;
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
        User.getInstance().save();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    /***
     * 切换账号
     * @param account
     */
    public static void switchAccount(TbAccount account, Context context) {
        switchUserInfo(account);

        if (activities != null && !activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }

        context.startActivity(new Intent(context, MainActivity.class));

    }

    private static void switchUserInfo(TbAccount account) {
        User user = User.getInstance();
        user.reset();
        user.setToken(account.getToken());
        user.setName(account.getName());
        user.setSex(account.getSex());
        user.setLoginName(account.getAccount());
        user.setLogin(true);
        user.setHasInfo(account.getHasExp());
        user.setBirthday(account.getBirthday());
        user.setHasExp(account.getHasExp());
        user.setLastLoginDate(account.getLastLoginDate());
        user.save();
    }
}
