package com.match.app.manager;

import android.content.Context;

import com.dyhdyh.widget.loading.dialog.LoadingDialog;

public class LoadingManager {

    public static void show(Context context, String msg) {
        LoadingDialog.make(context)
                .setMessage(msg)
                .setCancelable(false)
                .show();
    }

    public static void show(Context context) {
        LoadingDialog.make(context)
                .setMessage("加载中...")
                .setCancelable(false)
                .show();
    }

    public static void cansel(){
        //取消Loading
        LoadingDialog.cancel();
    }

}
