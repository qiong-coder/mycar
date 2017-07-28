package com.mycar.action;

import com.alibaba.fastjson.JSONObject;
import com.mycar.service.UserService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by qixiang on 7/28/17.
 */

@RestController
public class UserAction {

    private Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Resource
    UserService userService;

    @RequestMapping(value = "/user/orders/{identity}/{phone}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getOrders(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable("identity") String identity,
                                  @PathVariable("phone") String phone)
    {
        JSONObject ret = userService.getOrdersAndIdentityAndPhone(identity, phone);
        if ( ret == null ) return new HttpResponse(HttpStatus.NO_ORDER);
        return new HttpResponse(ret);
    }

}
