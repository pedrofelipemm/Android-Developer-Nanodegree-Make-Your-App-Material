package com.example.xyzreader.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    private static final String TAG = DateUtil.class.getName();

    private static final SimpleDateFormat dateFormatUS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.US);
    private static final DateFormat dateFormatDefault = SimpleDateFormat.getDateInstance();

    public static final GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);

    private DateUtil() {
    }

    public static String formatDefault(Date date) {
        return dateFormatDefault.format(date);
    }

    public static Date parseDefault(String date, Date defaultDate) {
        try {
            return dateFormatDefault.parse(date);
        } catch (ParseException e) {
            Log.d(TAG, e.getMessage(), e);
            return defaultDate;
        }
    }

    public static String formatUS(Date date) {
        return dateFormatUS.format(date);
    }

    public static Date parseUS(String date, Date defaultDate) {
        try {
            return dateFormatUS.parse(date);
        } catch (ParseException e) {
            Log.d(TAG, e.getMessage(), e);
            return defaultDate;
        }
    }
}
