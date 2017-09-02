package com.mycar.action;

import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.model.OrderStatusCount;
import com.mycar.service.OrderService;
import com.mycar.service.VehicleService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/17/17.
 */

@RestController
public class OrderAction {

    private static Logger logger = LoggerFactory.getLogger(OrderAction.class);

    @Resource
    OrderService orderService;

    @Resource
    VehicleService vehicleService;

    @RequestMapping(value = "/orders/{status}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse select(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("status") int status)
    {
        JSONObject ret = orderService.getOrdersAndVehicleInfosByStatus(status);
        if ( ret == null ) return new HttpResponse(HttpStatus.NO_ORDER);
        else return new HttpResponse(ret);
    }

    @RequestMapping(value = "/orders/number/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getOrdersNumberByStatus()
    {
        List<OrderStatusCount> ordersCounts = orderService.getOrdersNumberByStatus();
        return new HttpResponse(ordersCounts);
    }

    @RequestMapping(value = "/order/{oid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getOrderById(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @PathVariable("oid") long oid)
    {
        JSONObject ret = orderService.getOrderAndVehicleInfoByOrderId(oid);
        if ( ret == null ) return new HttpResponse(HttpStatus.NO_ORDER);
        else return new HttpResponse(ret);
    }

    @RequestMapping(value = "/order/{viid}/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse insert(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("viid") long viid,
                               @RequestBody Order order)
    {
        int status = orderService.insertOrder(viid,order);
        if ( status < 0 ) {
            logger.error("failure to insert the order - viid:{}\torder:{}",viid, order);
            return new HttpResponse(status);
        } else {
            logger.info("[insert][order:{}]",order);
            return new HttpResponse(order.getId());
        }
    }

    @RequestMapping(value = "/order/check/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse check(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable("oid") long oid)
    {
        return new HttpResponse(orderService.checkOrder(oid));
    }

    @RequestMapping(value = "/order/renting/{oid}/{number}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse rent(HttpServletRequest request,
                             HttpServletResponse response,
                             @PathVariable("oid") long id,
                             @PathVariable("number") String number,
                             @RequestBody Order order)
    {
        return new HttpResponse(orderService.rentOrder(id,order,number));
    }


    @RequestMapping(value = "/order/drawback/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse drawback(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("oid") long id,
                                 @RequestBody Order order)
    {
        return new HttpResponse(orderService.drawBackOrder(id,order));
    }

    @RequestMapping(value = "/order/finished/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse finished(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("oid") long id,
                                 @RequestBody(required = false) Order order)
    {
        return new HttpResponse(orderService.finishedOrder(id,order));
    }

    @RequestMapping(value = "/order/cancle/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse cancle(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("oid") long id,
                               @RequestBody Order order)
    {
        return new HttpResponse(orderService.cancleOrder(id, order));
    }

    @RequestMapping(value = "/order/history/{type}/{data}/{begin}/{end}/", method = RequestMethod.GET)
    public HttpResponse history(@PathVariable String type,
                                @PathVariable String data,
                                @PathVariable Long begin,
                                @PathVariable Long end)
    {
        Timestamp begin_stamp = new Timestamp(begin);
        Timestamp end_stamp = new Timestamp(end);
        return new HttpResponse(orderService.orderHistory(type, data, begin_stamp, end_stamp));
    }
}
