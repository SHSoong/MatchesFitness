package com.match.app.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScreenUtils {

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void setImageViewHeight(Context context, ImageView imageView) {
        int screenWidth = ScreenUtils.getScreenWidth(context);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = screenWidth;
        params.width = screenWidth;
        imageView.setLayoutParams(params);
    }
}
