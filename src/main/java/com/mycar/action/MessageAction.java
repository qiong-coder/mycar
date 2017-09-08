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
@RequestMapping("/message")
public class MessageAction {

    private Logger logger = LoggerFactory.getLogger(MessageAction.class);

    @Resource
    MessageService messageService;

    @RequestMapping(value = "/phone/{phone}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse get_message(@PathVariable("phone") String phone)
    {
        if ( !messageService.checkTimeout(phone) ) {
            logger.warn("message send is not timeout - phone:{}", phone);
            return new HttpResponse(HttpStatus.MESSAGE_SEND_TIMEOUT);
        } else return new HttpResponse(messageService.getCode(phone));
    }

    @RequestMapping(value = "/phone/{phone}/{code}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse check_message(@PathVariable("phone") String phone,
                                      @PathVariable("code") String code)
    {
        if ( messageService.checkCode(phone,code) ) return new HttpResponse(HttpStatus.OK);
        return new HttpResponse(HttpStatus.MESSAGE_CHECK_ERROR);
    }

    @RequestMapping(value = "/picture/", method = RequestMethod.GET)
    public HttpResponse get_picture() {
        String filename = messageService.getPicture();
        if ( filename != null ) return new HttpResponse(filename);
        else return new HttpResponse(HttpStatus.ERROR);
    }

    @RequestMapping(value = "/picture/{picture}/{code}/", method = RequestMethod.GET)
    public HttpResponse check_picture(@PathVariable String picture,
                                      @PathVariable String code) {
        int result = messageService.checkPicture(picture, code);
        if ( result == 0 ) return new HttpResponse(HttpStatus.OK);
        else if ( result == -1 ) return new HttpResponse(HttpStatus.CODE_ERROR);
        else if ( result == -2 ) return new HttpResponse(HttpStatus.CODE_NOT_FOUND);
        return new HttpResponse(HttpStatus.ERROR);
    }
}
