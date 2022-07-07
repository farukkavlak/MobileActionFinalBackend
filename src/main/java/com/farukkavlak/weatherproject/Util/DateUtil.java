package com.farukkavlak.weatherproject.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDateStr() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }
    public static String getLastWeekDateStr(Date date) {
        //1 week = 604800000 milliseconds
        date.setTime(date.getTime()-604800000);
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
    //Increase day by one
    public static Date plusOneDay(Date date) {
        //Add 1 day to first date to iterate loop all days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        return date;
    }
    //Increase day(timestamp) by one
    public static long plusOneDayForTimeStamp(long date) {
        return (date+86400);
    }
    public static String convertDateToString(Date date ){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
    public static Date convertStringToDate(String date_str) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = formatter.parse(date_str);
        } catch (ParseException e) {
            throw new RuntimeException("Format is wrong.");
        }
        return date;
    }
}
