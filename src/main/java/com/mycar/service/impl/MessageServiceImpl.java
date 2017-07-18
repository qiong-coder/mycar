package com.mycar.service.impl;

import com.mycar.service.MessageService;

/**
 * Created by qixiang on 7/18/17.
 */
public class MessageServiceImpl implements MessageService {


    @Override
    public String getCode(String phone) {
        return null;
    }

    @Override
    public int checkTimeout(String phone) {
        return 0;
    }

    @Override
    public int checkCode(String phone, String code) {
        return 0;
    }
}
