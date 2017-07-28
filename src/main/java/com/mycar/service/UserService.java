package com.mycar.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by qixiang on 7/28/17.
 */
public interface UserService {

    JSONObject getOrdersAndIdentityAndPhone(String identity, String phone);

}
