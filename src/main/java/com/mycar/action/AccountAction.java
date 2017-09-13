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
import java.util.List;

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
        Account account1 = accountService.login(account.getUsername(),account.getPassword());
        if ( account1 == null ) return new HttpResponse(HttpStatus.LOGIN_ERROR);

        session.setAttribute("token",account1.getToken());
        return new HttpResponse(account1);
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
        int uid = accountService.register(account);
        if ( uid == -1 ) return new HttpResponse(HttpStatus.DUPLICATE_ACCOUNT);
        return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpResponse list(HttpServletRequest request) {
        if ( accountService.check(request.getSession(),request.getHeader("token")) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        return new HttpResponse(accountService.list());
    }

    @RequestMapping(value = "/{username}/", method = RequestMethod.GET)
    public HttpResponse getByUsername(HttpServletRequest request,
                                      @PathVariable String username) {
        if ( accountService.check(request.getSession(),request.getHeader("token")) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        Account account = accountService.get(username);
        if ( account == null ) return new HttpResponse(HttpStatus.NO_ACCOUNT);
        else return new HttpResponse(account);
    }

    @RequestMapping(value = "/{username}/", method = RequestMethod.PUT)
    public HttpResponse updateByUsername(HttpServletRequest request,
                                         @PathVariable String username,
                                         @RequestBody Account account)
    {
        if ( accountService.check(request.getSession(),request.getHeader("token")) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        account.setUsername(username);
        if ( accountService.update(account) == 0 ) return new HttpResponse(HttpStatus.NO_ACCOUNT);
        else return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/", method = RequestMethod.DELETE)
    public HttpResponse deleteByUsername(HttpServletRequest request,
                                         @PathVariable String username)
    {
        if ( accountService.check(request.getSession(),request.getHeader("token")) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        if ( accountService.delete(username) == 0 ) return new HttpResponse(HttpStatus.NO_ACCOUNT);
        else {
            accountService.logout(request.getSession());
            return new HttpResponse(HttpStatus.OK);
        }
    }

}
