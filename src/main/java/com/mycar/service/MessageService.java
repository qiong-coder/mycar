package com.mycar.service;

/**
 * Created by qixiang on 7/18/17.
 */
public interface MessageService {

    String getCode(String phone);

    boolean checkTimeout(String phone);

    boolean checkCode(String phone, String code);

    String getPicture();

    int checkPicture(String picture, String code);

}
