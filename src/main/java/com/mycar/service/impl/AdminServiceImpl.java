package com.mycar.service.impl;

import com.mycar.mapper.AdminMapper;
import com.mycar.model.Admin;
import com.mycar.service.AdminService;
import com.mycar.utils.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qixiang on 8/1/17.
 */
public class AdminServiceImpl implements AdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminService.class);

    private static final String PREFIX="mycar-admin-";
    private static final int TIMEOUT = 60*15;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    CacheUtils cacheUtils;

    @Override
    public boolean check(String name, String password) {
        Admin admin = adminMapper.get(name);
        if ( admin == null ) {
            logger.warn("failure to find admin by name - name:{}",name);
            return false;
        } else if ( password.compareTo(admin.getPassword()) == 0 ) {
            logger.info("success login - name:{}",name);
            return true;
        } else return false;
    }

    @Override
    public void logout(String token) {}
}
