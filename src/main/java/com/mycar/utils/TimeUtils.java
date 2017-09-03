package com.mycar.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class TimeUtils {

    public static Timestamp GetTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public static boolean TimeInteraction(Timestamp begin, Timestamp end, Timestamp rbegin, Timestamp rend)
    {
        Timestamp now = GetTimestamp();
        if ( end.compareTo(now) <= 0 ) {
            return false;
        } else {
            return begin.compareTo(rbegin) >= 0 && begin.compareTo(rend) <= 0 ||
                    end.compareTo(rbegin) >= 0 && end.compareTo(rend) <= 0;
        }
    }

    public static int TimeDiff(Timestamp begin, Timestamp end)
    {
        return new Long(Math.round(Math.ceil((end.getTime() - begin.getTime()) / (3600*MILLIS_PER_SECOND*24.0)))).intValue();
    }

    public static int TimeDiffByDays(Timestamp begin, Timestamp end) {
        return (int)((end.getTime()/(3600*MILLIS_PER_SECOND)-8)/24-(begin.getTime()/(3600*MILLIS_PER_SECOND)-8)/24)+1;
    }

    public final static long MILLIS_PER_SECOND = 1000L;

    private static SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String GetSampleTime(Long expire)
    {
        Date now  = new Date(System.currentTimeMillis()+expire*MILLIS_PER_SECOND);
        return outFormat.format(now);
    }

    public static String GetDateFormat(Calendar calendar)
    {
        return dateFormat.format(calendar.getTime());
    }
}
