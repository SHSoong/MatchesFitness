package com.matches.fitness.utils;

import android.content.Context;

/**
 * Created by Administrator on 2018/7/7 0007.
 */

public class ScreenUtils {
    /**
     * dp转换成px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
