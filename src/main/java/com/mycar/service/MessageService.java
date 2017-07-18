package com.mycar.service;

/**
 * Created by qixiang on 7/18/17.
 */
public interface MessageService {

    String getCode(String phone);

    int checkTimeout(String phone);

    int checkCode(String phone, String code);

}
