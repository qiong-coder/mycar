package com.mycar.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;


/**
 * Created by stupid-coder on 9/2/17.
 */
public class Md5Utils {

    public static String base64(byte[] bytes)
    {
        return new BASE64Encoder().encode(bytes);
    }

    public static String md5(String name, String suffix)
    {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return base64(md5.digest((name+suffix).getBytes("utf-8")));
        } catch (Exception ex) {
            return name+suffix;
        }
    }

}
