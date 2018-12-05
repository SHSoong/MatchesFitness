package com.match.app.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.match.app.utils.DisplayUtils;

public class WordsNavigation extends View {
    private String words[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Context mContext;
    private Paint wordsPaint;
    private Paint bgPaint;
    private int itemWidth;
    private int itemHeight;
    private int touchIndex = 0;
    private OnWrodsChangeListener listener;

    public void setListener(OnWrodsChangeListener listener) {
        this.listener = listener;
    }
    /*设置当前按下的是那个字母*/
    public void setTouchIndex(String word) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word)) {
                touchIndex = i;
                invalidate();
                return;
            }
        }
    }

    public WordsNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        wordsPaint = new Paint();
        bgPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        int height = getMeasuredHeight() - 10;
        itemHeight = height / 26;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wordsPaint.setTextSize(DisplayUtils.sp2px(mContext, 14));
        for (int i = 0; i < words.length; i++) {
//            if (touchIndex == i) {
//                canvas.drawCircle(itemWidth / 2, itemHeight / 2 + i * itemHeight, DisplayUtils.sp2px(mContext, 10), bgPaint);
//                wordsPaint.setColor(Color.GRAY);
//            } else {
                wordsPaint.setColor(Color.WHITE);

//            }
            Rect rect = new Rect();
            wordsPaint.getTextBounds(words[i], 0, 1, rect);
            int wordWidth = rect.width();
            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = itemWidth / 2 + i * itemHeight;
            canvas.drawText(words[i], wordX, wordY, wordsPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / itemHeight);
                if (index != touchIndex) {
                    touchIndex = index;
                }
                if (listener != null && 0 < touchIndex && touchIndex <= words.length - 1) {
                    listener.wordsChange(words[touchIndex]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public interface OnWrodsChangeListener {
        void wordsChange(String words);
    }
}
