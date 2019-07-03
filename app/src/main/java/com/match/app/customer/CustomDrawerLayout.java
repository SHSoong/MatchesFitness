package com.match.app.customer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class CustomDrawerLayout extends DrawerLayout {

    private boolean isIntercept = true;
    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private float lastMotionX = 0f;
    private float lastMotionY = 0f;

    public CustomDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            float x = ev.getX();
            float y = ev.getY();

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    lastMotionX = x;
                    lastMotionY = y;
                }
                break;
                case MotionEvent.ACTION_MOVE: {
                    int xDiff = (int) Math.abs(x - lastMotionX);
                    int yDiff = (int) Math.abs(y - lastMotionY);
                    int xyDiff = xDiff * xDiff + yDiff * yDiff;
                    boolean xMoved = xyDiff > touchSlop * touchSlop;
                    if (xMoved) {
                        return xDiff > yDiff * 4;
                    }
                }
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setIsIntercept(boolean intercepted) {
        isIntercept = intercepted;
    }

}
