package com.mycar.action;

import com.mycar.service.WeiXinPayService;
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
 * Created by stupid-coder on 7/23/17.
 */

@RestController
public class WeiXinPayAction {

    private static Logger logger = LoggerFactory.getLogger(WeiXinPayAction.class);

    @Resource
    WeiXinPayService weiXinPayService;

    @RequestMapping(value="/pay/weixin/get/{oid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getPayUrl(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable("oid") long oid)
    {
        String pay_url = weiXinPayService.getPayUrl(oid,request.getRemoteAddr());
        if ( pay_url == null ) return new HttpResponse(HttpStatus.ERROR);
        return new HttpResponse(pay_url);
    }

    @RequestMapping(value="/pay/weixin/check/{oid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse check(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable("oid") long oid)
    {
        return new HttpResponse(weiXinPayService.checkPay(oid));
    }
}
