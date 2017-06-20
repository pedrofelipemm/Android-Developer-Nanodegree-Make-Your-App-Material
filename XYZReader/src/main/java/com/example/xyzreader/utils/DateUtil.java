package com.example.xyzreader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat dateFormatUS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.US);
    private static final SimpleDateFormat dateFormatDefault = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

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
            return defaultDate;
        }
    }
}
