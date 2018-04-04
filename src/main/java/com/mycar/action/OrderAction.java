package com.mycar.action;

import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.model.OrderStatusCount;
import com.mycar.response.OrderConflict;
import com.mycar.response.OrderHistory;
import com.mycar.response.OrderSchedule;
import com.mycar.service.AccountService;
import com.mycar.service.OrderService;
import com.mycar.service.VehicleService;
import com.mycar.utils.AccountRoles;
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
    AccountService accountService;

    @RequestMapping(value = "/orders/{status}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse select(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("status") int status)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        JSONObject ret = orderService.getOrdersAndVehicleInfosByStatus(status);
        if ( ret == null ) return new HttpResponse(HttpStatus.NO_ORDER);
        else return new HttpResponse(ret);
    }

    @RequestMapping(value = "/orders/number/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse getOrdersNumberByStatus()
    {
        List<OrderStatusCount> ordersCounts = orderService.getOrdersNumberByStatus();
        return new HttpResponse(ordersCounts);
    }

    @RequestMapping(value = "/order/{oid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse getOrderById(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @PathVariable("oid") long oid)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        JSONObject ret = orderService.getOrderAndVehicleInfoByOrderId(oid);
        if ( ret == null ) return new HttpResponse(HttpStatus.NO_ORDER);
        else return new HttpResponse(ret);
    }

    @RequestMapping(value = "/order/{viid}/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse insert(HttpServletRequest request,
                               @PathVariable("viid") long viid,
                               @RequestBody Order order)
    {
//        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        int status = orderService.insertOrder(viid,order);
        if ( status < 0 ) {
            logger.error("failure to insert the order - viid:{}\torder:{}",viid, order);
            return new HttpResponse(status);
        } else {
            logger.info("[insert][order:{}]",order);
            return new HttpResponse(order.getId());
        }
    }

    @RequestMapping(value = "/order/{oid}/", method = RequestMethod.PUT)
    public HttpResponse update(HttpServletRequest request,
                               @PathVariable Long oid,
                               @RequestBody Order order) {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        order.setId(oid);
        int status = orderService.updateOrder(order);
        if ( status == 0 ) return new HttpResponse(HttpStatus.NO_ORDER);
        else return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{oid}/", method = RequestMethod.DELETE)
    public HttpResponse delete(HttpServletRequest request,
                               @PathVariable Long oid) {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        int status = orderService.deleteOrderById(oid);
        if ( status == 0 ) return new HttpResponse(HttpStatus.NO_ORDER);
        else return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/order/check/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse check(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable("oid") long oid)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        return new HttpResponse(orderService.checkOrder(oid));
    }

    @RequestMapping(value = "/order/renting/{oid}/{number}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse rent(HttpServletRequest request,
                             HttpServletResponse response,
                             @PathVariable("oid") long id,
                             @PathVariable("number") String number,
                             @RequestBody Order order)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        return new HttpResponse(orderService.rentOrder(id,order,number));
    }


    @RequestMapping(value = "/order/drawback/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse drawback(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("oid") long id,
                                 @RequestBody Order order)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        return new HttpResponse(orderService.drawBackOrder(id,order));
    }

    @RequestMapping(value = "/order/finished/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse finished(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("oid") long id,
                                 @RequestBody(required = false) Order order)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        return new HttpResponse(orderService.finishedOrder(id,order));
    }

    @RequestMapping(value = "/order/cancle/{oid}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse cancle(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("oid") long id,
                               @RequestBody Order order)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        return new HttpResponse(orderService.cancleOrder(id, order));
    }

    @RequestMapping(value = "/order/history/{viid}/{number}/{begin}/{end}/", method = RequestMethod.GET)
    public HttpResponse history(HttpServletRequest request,
                                @PathVariable String viid,
                                @PathVariable String number,
                                @PathVariable Long begin,
                                @PathVariable Long end)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        Timestamp begin_stamp = new Timestamp(begin);
        Timestamp end_stamp = new Timestamp(end);
        OrderHistory history = orderService.orderHistory(viid.compareTo("null") == 0 ? null : Long.parseLong(viid),
                number.compareTo("null")==0?null:number,
                begin_stamp, end_stamp);
        if ( history == null ) return new HttpResponse(HttpStatus.ERROR);
        else return new HttpResponse(history);
    }

    @RequestMapping(value = "/order/schedule/{viid}/{begin}/{end}/", method = RequestMethod.GET)
    public HttpResponse schedule(HttpServletRequest request,
                                 @PathVariable String viid,
                                 @PathVariable Long begin,
                                 @PathVariable Long end)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        Timestamp begin_stamp = new Timestamp(begin);
        Timestamp end_stamp = new Timestamp(end);
        List<OrderSchedule> orderSchedules = orderService.orderSchedule(viid.compareTo("null") == 0 ? null : Long.parseLong(viid)
                , begin_stamp, end_stamp);
        if ( orderSchedules == null ) return new HttpResponse(HttpStatus.ERROR);
        else return new HttpResponse(orderSchedules);

    }

    @RequestMapping(value = "/order/conflict/{viid}/{begin}/{end}/", method = RequestMethod.GET)
    public HttpResponse conflict(HttpServletRequest request,
                                 @PathVariable Long viid,
                                 @PathVariable Long begin,
                                 @PathVariable Long end)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        Timestamp begin_stamp = new Timestamp(begin);
        Timestamp end_stamp = new Timestamp(end);
        OrderConflict orderConflict = orderService.orderConflict(viid, begin_stamp, end_stamp);
        if ( orderConflict == null ) return new HttpResponse(HttpStatus.ERROR);
        else return new HttpResponse(orderConflict);
    }
}
