package com.mycar.action;

import com.mycar.service.MessageService;
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
 * Created by qiong-coder on 7/18/17.
 */

@RestController
public class MessageAction {

    private Logger logger = LoggerFactory.getLogger(MessageAction.class);

    @Resource
    MessageService messageService;

    @RequestMapping(value = "/message/{phone}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse message(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable("phone") String phone)
    {
        if ( !messageService.checkTimeout(phone) ) {
            logger.warn("message send is not timeout - phone:{}", phone);
            return new HttpResponse(HttpStatus.MESSAGE_SEND_TIMEOUT);
        } else return new HttpResponse(messageService.getCode(phone));
    }

    @RequestMapping(value = "/message/{phone}/{code}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse check(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable("phone") String phone,
                              @PathVariable("code") String code)
    {
        if ( messageService.checkCode(phone,code) ) return new HttpResponse(HttpStatus.OK);
        return new HttpResponse(HttpStatus.MESSAGE_CHECK_ERROR);
    }

}
