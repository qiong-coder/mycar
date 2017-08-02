package com.mycar.service;

/**
 * Created by qixiang on 8/1/17.
 */
public interface AdminService {

    boolean check(String name, String password);

    void logout(String token);
}
