package com.match.app.customer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimePicker {
    Context context;

    private OnDatePickerListener onDatePickerListener;
    private OnTimePickerListener onTimePickerListener;
    private OnDateTimePickerListener onDateTimePickerListener;

    public DateTimePicker(Context context, OnDatePickerListener onDatePickerListener) {
        this.context = context;
        this.onDatePickerListener = onDatePickerListener;
    }

    public DateTimePicker(Context context, OnTimePickerListener onTimePickerListener) {
        this.context = context;
        this.onTimePickerListener = onTimePickerListener;
    }

    public DateTimePicker(Context context, OnDateTimePickerListener onDateTimePickerListener) {
        this.context = context;
        this.onDateTimePickerListener = onDateTimePickerListener;
    }

    public void datePicker() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String dateStr = year + "-" + (month + 1) + "-" + dayOfMonth;
                if (onDatePickerListener != null)
                    onDatePickerListener.setOnDatePickerListener(dateStr);
            }
        }, mYear, mMonth, mDay).show();
    }

    public void timePicker() {
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeStr = hourOfDay + ":" + minute;
                if (onTimePickerListener != null)
                    onTimePickerListener.setOnTimePickerListener(timeStr);
            }
        }, 0, 0, true).show();
    }

    public void dateTimePicker() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                final String dateStr = year + "-" + (month + 1) + "-" + dayOfMonth;
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeStr = hourOfDay + ":" + minute;
                        if (onDateTimePickerListener != null)
                            onDateTimePickerListener.setOnDateTimePickerListener(dateStr + " " + timeStr);
                    }
                }, 0, 0, true).show();
            }
        }, mYear, mMonth, mDay).show();
    }

    public interface OnDatePickerListener {
        void setOnDatePickerListener(String dateStr);
    }

    public interface OnTimePickerListener {
        void setOnTimePickerListener(String timeStr);
    }

    public interface OnDateTimePickerListener {
        void setOnDateTimePickerListener(String dateStr);
    }
}
