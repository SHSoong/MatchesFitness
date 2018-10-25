package com.match.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
}
