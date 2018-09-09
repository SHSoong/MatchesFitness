package com.matches.fitness.ui.login;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.matches.fitness.utils.DisplayUtils;

public class WeekNavigation extends View {
    private String weeks[] = {"一", "二", "三", "四", "五", "六", "天"};
    private Context mContext;
    private Paint wordsPaint;
    private int itemWidth;

    public WeekNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        wordsPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        itemWidth = width / 7;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wordsPaint.setTextSize(DisplayUtils.sp2px(mContext, 14));
        for (int i = 0; i < weeks.length; i++) {
            if (5 == i || 6 == i) {
                wordsPaint.setColor(Color.GRAY);
            } else {
                wordsPaint.setColor(Color.BLACK);
            }
            Rect rect = new Rect();
            wordsPaint.getTextBounds(weeks[i], 0, 1, rect);
            int wordWidth = rect.width();
            float wordX = i * itemWidth + itemWidth/2 ;
            float wordY = wordWidth ;
            canvas.drawText(weeks[i], wordX, wordY, wordsPaint);
        }
    }

}
