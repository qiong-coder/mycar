package com.mycar.utils;

/**
 * Created by qiong-coder on 8/1/17.
 */
public class OrderUtils {

    public static String getOrderId(long vid, long sid)
    {
        return String.format("%s%02X%02X%02X",TimeUtils.GetSampleTime(0L),sid,vid,(int)(Math.random()*100));
    }

}
