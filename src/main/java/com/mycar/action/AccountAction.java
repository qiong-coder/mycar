package com.mycar.action;

import com.mycar.model.Account;
import com.mycar.service.AccountService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by qiong-coder on 7/31/17.
 */

@RestController
@RequestMapping(value = "/account")
public class AccountAction {

    private Logger logger = LoggerFactory.getLogger(AccountAction.class);

    @Resource
    AccountService accountService;

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public HttpResponse login(HttpSession session,
                              @RequestBody Account account)
    {
        String token = accountService.login(account.getName(),account.getPassword());
        if ( token == null ) return new HttpResponse(HttpStatus.LOGIN_ERROR);
        session.setAttribute("token",token);
        return new HttpResponse(token);
    }

    @RequestMapping(value = "/logout/", method = RequestMethod.PUT)
    public HttpResponse logout(HttpSession session,
                               @RequestHeader(name = "token") String token)
    {
        if ( accountService.check(session,token) != 0 ) return new HttpResponse(HttpStatus.LOGOUT_ERROR);
        accountService.logout(session);
        return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public HttpResponse register(@RequestBody Account account)
    {
        int uid = accountService.register(account.getName(),account.getPassword());
        if ( uid == -1 ) return new HttpResponse(HttpStatus.ERROR);
        return new HttpResponse(HttpStatus.OK);
    }

}
