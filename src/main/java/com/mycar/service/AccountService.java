package com.mycar.service;

import com.mycar.model.Account;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by qixiang on 8/1/17.
 */
public interface AccountService {

    int register(Account account);

    Account get(String username);

    List<Account> list();

    Account login(String username, String password);

    void logout(HttpSession session);

    int update(Account account);

    int check(HttpSession session, String token);

    int delete(String username);
}
