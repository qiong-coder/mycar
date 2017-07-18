package com.mycar.utils;

/**
 * Created by qixiang on 7/18/17.
 */
public interface CacheUtils {

    void put(String key, String value, int timeout);

    String get(String key);

    void delete(String key);

}
