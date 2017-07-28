package com.mycar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mycar.service.OrderService;
import com.mycar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qixiang on 7/28/17.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    OrderService orderService;

    @Override
    public JSONObject getOrdersAndIdentityAndPhone(String identity, String phone) {
        return orderService.getOrdersAndVechileInfosByIdentityAndPhone(identity, phone);
    }
}
