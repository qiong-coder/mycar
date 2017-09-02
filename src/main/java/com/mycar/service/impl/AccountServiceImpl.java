package com.mycar.service.impl;

import com.mycar.mapper.AccountMapper;
import com.mycar.model.Account;
import com.mycar.service.AccountService;
import com.mycar.utils.CacheUtils;
import com.mycar.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by qixiang on 8/1/17.
 */
public class AccountServiceImpl implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static final String PREFIX="mycar-admin-";
    private static final int TIMEOUT = 60*15;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public int register(String name, String password) {
        if ( accountMapper.get(name) != null ) return -1;
        return accountMapper.insert(name,password);
    }

    @Override
    public String login(String name, String password) {
        Account account = accountMapper.get(name);
        if ( account == null ) return null;
        else if ( account.getPassword().compareTo(password) != 0 ) return null;
        else return Md5Utils.md5(name,Long.toString(System.currentTimeMillis()));
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute("token");
    }

    @Override
    public int update(String name, String password) {
        return 0;
    }

    @Override
    public int check(HttpSession session, String token) {
        String session_token = (String)session.getAttribute("token");
        if ( session_token == null || session_token.compareTo(token) != 0 ) return -1;
        else return 0;
    }
}
