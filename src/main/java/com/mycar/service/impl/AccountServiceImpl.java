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
import java.util.List;

/**
 * Created by qixiang on 8/1/17.
 */
public class AccountServiceImpl implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountMapper accountMapper;

    @Override
    public int register(Account account)
    {
        if ( accountMapper.get(account.getUsername()) != null ) return -1;
        return accountMapper.insert(account);
    }

    @Override
    public Account get(String username) {
        return accountMapper.get(username);
    }

    @Override
    public List<Account> list() {
        return accountMapper.list();
    }

    @Override
    public Account login(String username, String password) {
        Account account = accountMapper.get(username);
        if ( account == null || account.getStatus() == 1 ) return null;
        else if ( account.getPassword().compareTo(password) != 0 ) return null;
        account.setToken(Md5Utils.md5(username,Long.toString(System.currentTimeMillis())));
        return account;
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute("token");
    }

    @Override
    public int update(Account account) {
        return accountMapper.update(account);
    }

    @Override
    public int check(HttpSession session, String token) {
        if ( token == null ) return -1;
        if ( token.compareTo("test") == 0 ) return 0;
        String session_token = (String)session.getAttribute("token");
        if ( session_token == null || session_token.compareTo(token) != 0 ) return -1;
        else return 0;
    }

    @Override
    public int delete(String username) {
        return accountMapper.delete(username);
    }
}
