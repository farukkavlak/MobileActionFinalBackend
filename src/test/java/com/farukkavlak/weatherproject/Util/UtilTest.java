package com.farukkavlak.weatherproject.Util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilTest {
    private static SimpleDateFormat formatterDate;

    @BeforeAll
    public static void setup(){
        formatterDate = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    void shouldConvertStringToDate(){
        String dateStr = "10-10-2021";

        Date date = DateUtil.convertStringToDate(dateStr);

        String formatDate = formatterDate.format(date);

        assertEquals("10-10-2021",formatDate);
    }
    @Test
    void shouldNotConvertStringToDateError(){
        String dateStr = "10---10-2021";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,() ->DateUtil.convertStringToDate(dateStr));
        assertEquals(runtimeException.getMessage(),"Format is wrong.");
    }
    @Test
    void shouldConvertDateToString(){
        //01-01-2022
        Date date = new Date(1640995200000L);

        String dateStr = DateUtil.convertDateToString(date);

        assertEquals("01-01-2022",dateStr);
    }
    @Test
    void shouldGetCurrentDateString(){
        String currentDateTestStr = formatterDate.format(new Date());
        String currentDateStr = DateUtil.getCurrentDateStr();
        assertEquals(currentDateTestStr,currentDateStr);
    }

    @Test
    void shouldLastWeekDateString(){
        //09-01-2022 = 1641686400000L
        Date currentDate = new Date(1641686400000L);
        //02-01-2022 = 1641081600000L
        Date lastWeekDate = new Date(1641081600000L);
        //Manuel last week for test
        String lastWeekDateTestStr = formatterDate.format(lastWeekDate);
        //Get last week with DateUtil
        String lastWeekDateUtilStr = DateUtil.getLastWeekDateStr(currentDate);
        assertEquals(lastWeekDateUtilStr,lastWeekDateTestStr);
    }
    @Test
    void shouldPlusOneDay(){
        // 06-06-2022 = 1654473600000L in milliseconds
        Date firstDate = new Date(1654473600000L);
        // 07-06-2022 = 1654560000000L in milliseconds
        Date plusDate = new Date(1654560000000L);
        Date plusUtilDate = DateUtil.plusOneDay(firstDate);
        assertEquals(plusDate,plusUtilDate);
    }
    @Test
    void shouldPlusOneDayForTimeStamp(){
        // 06-06-2022 = 1654473600L in seconds
        Date firstDate = new Date(1654473600L);
        // 07-06-2022 = 1654560000L in seconds
        Date plusDate = new Date(1654560000L);
        Long plusUtilDate = DateUtil.plusOneDayForTimeStamp(firstDate.getTime());
        assertEquals(plusDate.getTime(),plusUtilDate);
    }
}
