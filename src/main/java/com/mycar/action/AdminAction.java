package com.mycar.action;

import com.mycar.model.Admin;
import com.mycar.service.AdminService;
import com.mycar.utils.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qiong-coder on 7/31/17.
 */

@RestController
public class AdminAction {

    private Logger logger = LoggerFactory.getLogger(AdminAction.class);

    @Resource
    AdminService adminService;

    @RequestMapping(value = "/admin/login/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse login(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestBody Admin admin)
    {

        return  new HttpResponse(null);
    }


    @RequestMapping(value = "/admin/logout/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse logout(HttpServletRequest request,
                               HttpServletResponse response)
    {
        return new HttpResponse(null);
    }
}
