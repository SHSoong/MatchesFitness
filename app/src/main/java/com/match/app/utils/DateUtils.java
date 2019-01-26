package com.match.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String longToDate(long lo) {
        Date date = new Date(lo);
        return sdf.format(date);
    }

    //出生日期字符串转化成Date对象
    public static Date parse(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getTitleDay(String day){
        try {
            switch (JudgmentDay(day)) {
                case YESTERDY : {
                    return "今天";
                }
                case TODAY : {
                    return "昨天";
                }
                case TOMORROWDAT : {
                    return "明天";
                }
                default:
                    return day;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final int YESTERDY = -1;
    private static final int TODAY = 0;
    private static final int TOMORROWDAT = 1;
    private static final int OTHER_DAY = 10000;

    public static int JudgmentDay(String day) throws ParseException {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = sdf.parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            switch (diffDay) {
                case YESTERDY : {
                    return YESTERDY;
                }
                case TODAY : {
                    return TODAY;
                }
                case TOMORROWDAT : {
                    return TOMORROWDAT;
                }
            }
        }
        return OTHER_DAY;
    }

}
