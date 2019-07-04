package com.match.app;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.match.app.config.AppConstant;
import com.match.app.config.BuildConfig;
import com.match.app.message.entity.User;
import com.match.app.ui.login.LoginActivity;
import com.matches.fitness.R;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengAdHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "MyApp";

    public static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        this.registerActivityLifecycleCallbacks(this);

        UMConfigure.init(this, BuildConfig.umengAppKey, "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.umengMessageSecret);
        PushAgent mPushAgent = PushAgent.getInstance(this);

        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        //显示通知
        UmengAdHandler umengAdHandler = new UmengAdHandler() {
            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                Log.i(TAG, "text:" + uMessage.text + " builder_id:" + uMessage.builder_id);
                if (!TextUtils.isEmpty(uMessage.text)) {
                    // 消息体
                    Intent intent = new Intent();
                    intent.setAction("com.matches.fitness.news");
                    intent.putExtra(AppConstant.KEY_MESSAGE, uMessage.text);
                    sendBroadcast(intent);
                }

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(BuildConfig.PUSH_CHANNEL_ID, BuildConfig.PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                    if (manager != null) {
                        manager.createNotificationChannel(channel);
                    }
                    builder = new NotificationCompat.Builder(context, BuildConfig.PUSH_CHANNEL_ID);
                }

                RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                        R.layout.notification_view);
                myNotificationView.setTextViewText(R.id.notification_title, uMessage.title);
                myNotificationView.setTextViewText(R.id.notification_text, uMessage.text);
                myNotificationView.setTextViewText(R.id.notification_app_name, context.getString(context.getApplicationInfo().labelRes));
                myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, uMessage));

                builder.setContent(myNotificationView)
                        .setSmallIcon(getSmallIconId(context, uMessage))
                        .setTicker(uMessage.ticker)
                        .setAutoCancel(true);

                return builder.build();
            }
        };
        mPushAgent.setMessageHandler(umengAdHandler);

        //打开通知
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                Log.i(TAG, "-------->  dealWithCustomAction");
                super.dealWithCustomAction(context, uMessage);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    private void createNotification(Context context, UMessage uMessage, UmengAdHandler umengAdHandler) {

    }

    private static List<Activity> activities = new ArrayList<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
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
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        User.getInstance().setLogin(false);
        User.getInstance().save();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}
