package com.mycar.service;

/**
 * Created by stupid-coder on 7/23/17.
 */
public interface WeiXinPayService {

    String getPayUrl(Long oid, String client_ip);

    boolean checkPay(Long oid);

}
