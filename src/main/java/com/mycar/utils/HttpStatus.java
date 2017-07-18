package com.mycar.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qixiang on 7/18/17.
 */
public class HttpStatus {

    public static int OK = 0;
    public static int ERROR = -1;
    public static int NO_VEHICLE = -2;
    public static int NO_VEHICLE_INFO = -3;
    public static int NO_ORDER = -4;
    public static int ORDER_STATUS_ERROR = -5;
    public static int MESSAGE_CHECK_ERROR = -6;
    public static int MESSAGE_SEND_TIMEOUT = -7;

    private static Map<Integer, String> statusInfos = new HashMap<Integer,String>() {{
       put(OK,"success");
       put(ERROR,"error");
       put(NO_VEHICLE,"failure to find the vehicle");
       put(NO_VEHICLE_INFO, "failure to find the vehicle info");
       put(NO_ORDER, "failure to find the order");
       put(ORDER_STATUS_ERROR, "order's status is not right");
       put(MESSAGE_CHECK_ERROR, "message check is error");
       put(MESSAGE_SEND_TIMEOUT, "message send is not timeout");
    }};

    public static String getInfo(int status) {
        if ( statusInfos.containsKey(status) ) return statusInfos.get(status);
        else return null;
    }

}
