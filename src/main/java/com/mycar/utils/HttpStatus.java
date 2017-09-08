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
    public static int NO_VEHICLE_COST_INFO = -8;
    public static int NO_STORE = -9;
    public static int DUPLICATE_VEHICLE = -10;
    public static int DUPLICATE_STORE = -11;
    public static int LOGIN_ERROR = -12;
    public static int LOGOUT_ERROR = -13;
    public static int CODE_ERROR = -14;
    public static int CODE_NOT_FOUND = -15;

    private static Map<Integer, String> statusInfos = new HashMap<Integer,String>() {{
       put(OK,"success");
       put(ERROR,"error");
       put(NO_VEHICLE,"failure to find the vehicle");
       put(NO_VEHICLE_INFO, "failure to find the vehicle info");
       put(NO_ORDER, "failure to find the order");
       put(ORDER_STATUS_ERROR, "order's status is not right");
       put(MESSAGE_CHECK_ERROR, "message check is error");
       put(MESSAGE_SEND_TIMEOUT, "message send is not timeout");
       put(NO_VEHICLE_COST_INFO, "failure to find the vehicle cost info");
       put(NO_STORE, "failure to find the store info");
       put(DUPLICATE_VEHICLE, "the vehicle's number duplicate");
       put(DUPLICATE_STORE, "the store's address duplicate");
       put(LOGIN_ERROR, "failure to login the account");
       put(LOGOUT_ERROR, "failure to logout");
       put(CODE_ERROR, "failure to check the code");
       put(CODE_NOT_FOUND, "failure to find the code, maybe timeout, please get the new picture");
    }};

    public static String getInfo(int status) {
        if ( statusInfos.containsKey(status) ) return statusInfos.get(status);
        else return null;
    }

}
