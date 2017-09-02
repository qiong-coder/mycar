package com.mycar.service;

import javax.servlet.http.HttpSession;

/**
 * Created by qixiang on 8/1/17.
 */
public interface AccountService {

    int register(String name, String password);

    String login(String name, String password);

    void logout(HttpSession session);

    int update(String name, String password);

    int check(HttpSession session, String token);
}
