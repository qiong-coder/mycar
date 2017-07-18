package com.mycar.service.impl;

import com.mycar.service.MessageService;
import com.mycar.utils.CacheUtils;
import com.mycar.utils.TelVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qixiang on 7/18/17.
 */
public class MessageServiceImpl implements MessageService {


    private static final String CODE_PREFIX="mycar-code-";
    private static final String TIMEOUT_PREFIX="mycar-timeout-";
    private static final int CODE_TIMEOUT = 60*15;
    private static final int TIMEOUT_TIMEOUT = 60;

    @Autowired
    CacheUtils cacheUtils;

    @Override
    public String getCode(String phone) {
        //String code = TelVerificationUtil.SendCode(phone);
        String code="4444";
        if ( code != null ) {
            cacheUtils.delete(CODE_PREFIX+phone);
            cacheUtils.delete(TIMEOUT_PREFIX+phone);
            cacheUtils.put(CODE_PREFIX+phone, code, CODE_TIMEOUT);
            cacheUtils.put(TIMEOUT_PREFIX+phone,"t",TIMEOUT_TIMEOUT);
        }
        return code;
    }

    @Override
    public boolean checkTimeout(String phone) {
        return cacheUtils.get(TIMEOUT_PREFIX+phone) == null;
    }

    @Override
    public boolean checkCode(String phone, String code) {
        String c = cacheUtils.get(CODE_PREFIX+phone);
        if ( c == null ) return false;
        else return code.compareTo(c) ==0 ;
    }
}
