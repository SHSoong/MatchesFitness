package com.match.app.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context context, String message) {
        if (toast == null) toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();

    }

    public static void showLongToast(Context context, String message) {
        if (toast == null) toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();
    }
}
