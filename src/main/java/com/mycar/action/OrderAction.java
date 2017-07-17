package com.mycar.action;

import com.mycar.model.Order;
import com.mycar.service.OrderService;
import com.mycar.utils.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by stupid-coder on 7/17/17.
 */

@RestController
public class OrderAction {

    private static Logger logger = LoggerFactory.getLogger(OrderAction.class);

    @Resource
    OrderService orderService;

    @RequestMapping(value = "/order/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse insert(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestBody Order order)
    {
        return new HttpResponse(orderService.insertOrder(order));
    }

    @RequestMapping(value = "/order/check/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse check(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable("id") Long id)
    {
        return new HttpResponse(orderService.checkOrder(id));
    }


}
