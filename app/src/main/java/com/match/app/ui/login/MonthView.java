package com.match.app.ui.login;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.match.app.utils.DisplayUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthView extends View {

    private int year;
    private int month;
    private List<String> days;

    private Context mContext;
    private Paint dayPaint;
    private Paint ChoicePaint;
    private int itemWidth;
    private int itemHeight;
    private int touchIndex = 0;
    private int heightPosition;
    private int widthPosition;

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        dayPaint = new Paint();
        ChoicePaint = new Paint();
        getCurrentDate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        itemWidth = getMeasuredWidth();
        itemWidth = getMeasuredHeight() / 7;
//        itemHeight = height / 26;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dayPaint.setTextSize(DisplayUtils.sp2px(mContext, 14));
//        ChoicePaint.setTextSize(DisplayUtils.sp2px(mContext, 14));
        int week = Integer.valueOf(getWeekday(days.get(0)));
        for (String day : days) {
            heightPosition = (days.lastIndexOf(day) + 1 - (7 - week + 1)) / 7;
            widthPosition = week;
            if (week == 6 || week == 7) {
                dayPaint.setColor(Color.GRAY);
            } else {
                dayPaint.setColor(Color.BLACK);
            }

            Rect rect = new Rect();
            dayPaint.getTextBounds(day, day.length() - 2, day.length(), rect);
            int wordWidth = rect.width();
            float wordX = itemWidth * (week-1) + wordWidth / 2;
            float wordY = wordWidth / 2 + heightPosition * itemHeight;
            canvas.drawText(day, wordX, wordY, dayPaint);

            if (week == 7) {
                week = 1;
            } else {
                week++;
            }
        }
    }

    public String getWeekday(String date) {//必须yyyy-MM-dd
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }

    public void setDate(String date) {
        if (!TextUtils.isEmpty(date) && date.contains("/")
                && date.split("/").length == 2) {
            year = Integer.valueOf(date.split("/")[0]);
            month = Integer.valueOf(date.split("/")[1]);
            days = getDayByMonth(year, month);
            invalidate();
        }
    }


    public List<String> getDayByMonth(int yearParam, int monthParam) {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.set(Integer.valueOf(yearParam), Integer.valueOf(monthParam), 1);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = null;
            if (month < 10 && i >= 10) {
                aDate = String.valueOf(year) + "-0" + month + "-" + i;
            }
            if (month >= 10 && i < 10) {
                aDate = String.valueOf(year) + "-" + month + "-0" + i;
            }
            if (month >= 10 && i >= 10) {
                aDate = String.valueOf(year) + "-" + month + "-" + i;
            }
            list.add(aDate);
        }
        return list;
    }

    public void getCurrentDate() {
        Calendar cale = Calendar.getInstance();
        year = cale.get(Calendar.YEAR);
        month = cale.get(Calendar.MONTH) + 1;
        days = getDayByMonth(year, month);
    }
}
