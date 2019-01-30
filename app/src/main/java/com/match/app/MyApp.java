package com.match.app;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.match.app.common.User;
import com.match.app.config.AppConstant;
import com.match.app.config.BuildConfig;
import com.match.app.message.entity.Account;
import com.match.app.message.entity.Message;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.ui.login.LoginActivity;
import com.matches.fitness.R;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengAdHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Stack;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "MyApp";

    private static MyApp app;

    public static Context getContext() {
        return app;
    }

    private static Stack<Activity> activities;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

//        handler.sendEmptyMessageDelayed(0, 30000);

        registerActivityLifecycleCallbacks(this);

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
        UmengAdHandler umengAdHandler = new UmengAdHandler() {
            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                Log.i(TAG, "-------->  getNotification");

                switch (uMessage.builder_id) {
                    case 1:
                        if (!TextUtils.isEmpty(uMessage.text)) {
                            // 消息体
                            Intent intent = new Intent();
                            intent.setAction("com.matches.fitness.news");
                            intent.putExtra(AppConstant.KEY_MESSAGE, uMessage.text);
                            sendBroadcast(intent);
                        }

                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, uMessage.title);
                        myNotificationView.setTextViewText(R.id.notification_text, uMessage.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, uMessage));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
                                getSmallIconId(context, uMessage));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, uMessage))
                                .setTicker(uMessage.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, uMessage);
                }
            }
        };

        mPushAgent.setMessageHandler(umengAdHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                Log.i(TAG, "-------->  dealWithCustomAction");
                super.dealWithCustomAction(context, uMessage);
            }
        };

        mPushAgent.setNotificationClickHandler(notificationClickHandler);


    }

    private void testMessage(int count) {
        Message message = new Message();
        message.setConversation("123" + count);
        message.setContent("测试" + count);
        message.setHisLogoUrl("");
        message.setReceiver("234");
        message.setReceiverName("接受者");
        message.setSpeaker("123" + count);
        message.setSpeakerName("说话者" + count);
        message.setTime((int) (System.currentTimeMillis() / 1000));
        message.setStatus(3);
        Intent intent = new Intent();
        intent.setAction("com.matches.fitness.news");
        intent.putExtra(AppConstant.KEY_MESSAGE, new Gson().toJson(message));
        sendBroadcast(intent);

        handler.sendEmptyMessageDelayed(0, 30000);


    }

    int cout = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            testMessage(cout++);
        }
    };

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
        User.getInstance().reset();
        User.getInstance().save();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    /***
     * 切换账号
     * @param account a
     */
    public static void switchAccount(Account account, Context context) {
        switchUserInfo(account);

        if (activities != null && !activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }

        context.startActivity(new Intent(context, MainActivity.class));

    }

    private static void switchUserInfo(Account account) {
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
