package com.mycar.service.impl;



import com.alibaba.fastjson.JSONObject;
import com.mycar.logic.VehicleCostLogic;
import com.mycar.mapper.OrderMapper;
import com.mycar.model.*;
import com.mycar.service.OrderService;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.service.VehicleService;
import com.mycar.service.WeiXinPayService;
import com.mycar.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/18/17.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private WeiXinPayService weiXinPayService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private VehicleInfoCostService vehicleInfoCostService;

    private static void mergeInfo(Order l, Order r)
    {
        if ( r.getPay_info() != null )
            l.setPay_info(VehicleCostLogic.mergeInfo(l.getPay_info(),r.getPay_info()));

        if ( r.getCost_info() != null )
            l.setCost_info(VehicleCostLogic.mergeInfo(l.getCost_info(),r.getCost_info()));
    }

    @Override
    public Order getOrderById(long id) {
        Order order = orderMapper.getOrderById(id);
        if ( order == null ) logger.warn("falure to find the order by id - id:{}", id);
        return order;
    }

    @Override
    public List<Order> getOrdersByStatus(int status) {
        return orderMapper.getOrdersByStatus(status);
    }

    @Override
    public List<OrderStatusCount> getOrdersNumberByStatus() {
        List<OrderStatusCount> statusCounts = orderMapper.getOrdersNumberByStatus();
        if ( statusCounts == null && statusCounts.isEmpty() )logger.warn("failure to find the status number");
        return statusCounts;
    }

    @Override
    public List<Order> getOrdersByIdentityAndPhone(String identity, String phone) {
        List<Order> orders = orderMapper.getOrdersByIdentityAndPhone(identity,phone);
        if ( orders == null || orders.isEmpty() ) logger.warn("failure to find the order by identity-{} and phone-{}", identity,phone);
        return orders;
    }

    private JSONObject mergetVehicleInfoAndVehicle(List<Order> orderList) {
        JSONObject ret = new JSONObject();
        ret.put("vehicleInfos",vehicleService.getVehicleInfosByOrders(orderList));
        ret.put("vehicles", vehicleService.getVehiclesByOrders(orderList));
        ret.put("orders", orderList);
        return ret;
    }

    @Override
    public JSONObject getOrdersAndVehicleInfosByStatus(int status) {
        List<Order> orderList = getOrdersByStatus(status);

        if ( orderList != null && !orderList.isEmpty() ) {
           return mergetVehicleInfoAndVehicle(orderList);
        } else return null;
    }

    @Override
    public JSONObject getOrdersAndVechileInfosByIdentityAndPhone(String identity, String phone) {
        List<Order> orderList = getOrdersByIdentityAndPhone(identity, phone);

        if ( orderList != null && !orderList.isEmpty()) {
           return mergetVehicleInfoAndVehicle(orderList);
        } else return null;
    }

    @Override
    public JSONObject getOrderAndVehicleInfoByOrderId(long id) {
        JSONObject ret = null;
        Order order = getOrderById(id);
        if ( order != null ) {
            VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(order.getViid());
            if ( vehicleInfo != null ) {
                ret = new JSONObject();
                ret.put("order",order);
                ret.put("vehicleInfo", vehicleInfo);

            }
            if ( order.getVid() != null ) {
                Vehicle vehicle = vehicleService.getVehicleById(order.getVid());
                if ( vehicle != null ) ret.put("vehicle", vehicle);
            }
        } return ret;
    }

    @Override
    public int insertOrder(long viid, Order order) {
        VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(viid);
        if ( vehicleInfo == null ) {
            logger.warn("failure to get the vehicle info by id - id:{}", viid);
            return HttpStatus.NO_VEHICLE_INFO;
        }

        order.setViid(viid);
        order.setOid(OrderUtils.getOrderId(order.getViid(),order.getRent_sid()));
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostService.getVehicleInfoCostById(viid);

        int pay = VehicleCostLogic.getPreCostInfo(vehicleInfoCost,order);

        order.setPay_info(VehicleCostLogic.getPayInfo(pay, order.getName()));

        order.setCost_info("[]");
        if ( orderMapper.insertOrder(order) != 1 ) return HttpStatus.ERROR;

        return HttpStatus.OK;
    }

    @Override
    public int checkOrder(long oid) {
        Order order = getOrderById(oid);
        if ( order == null ) return HttpStatus.NO_ORDER;

        OrderStatus status = OrderStatus.values()[order.getStatus()];

        if ( status.compareTo(OrderStatus.PENDING) == 0 ) return HttpStatus.OK;

        if ( status.compareTo(OrderStatus.UNPAID) != 0 ) {
            logger.error("order's status is not unpaid - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        } else if (  weiXinPayService.checkPay(oid) ) {
            order.setStatus(OrderStatus.PENDING.getStatus());
            if ( orderMapper.updateStatus(order) == 1 ) return HttpStatus.OK;
            else {
                logger.error("failure to update the status to PENDING");
                return HttpStatus.ERROR;
            }
        } else {
            logger.error("failure to check the order's pay status - {}",order);
            return HttpStatus.ERROR;
        }
    }

    @Override
    public int rentOrder(long id, Order order, String number) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(number);
        if ( vehicle == null ) {
            logger.warn("failure to find the vehicle by number:{}",number);
            return HttpStatus.NO_VEHICLE;
        }

        Order o = getOrderById(id);
        if ( o == null ) return HttpStatus.NO_ORDER;

        OrderStatus status = OrderStatus.values()[o.getStatus()];
        if ( status != OrderStatus.PENDING ) {
            logger.warn("the order' status is not pending - order:{}", o);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        o.setVid(vehicle.getId());
        o.setRbegin(order.getRbegin());
        if ( order.getRend() != null ) o.setRend(order.getRend());

        o.setRrent_sid(order.getRrent_sid());
        if ( order.getRreturn_sid() != null ) o.setRreturn_sid(order.getRreturn_sid());

        vehicle.setBegin(o.getRbegin()); vehicle.setEnd(o.getRend());
        vehicle.setStatus(VehicleStatus.RENTING.getStatus()); vehicle.setSid(o.getRrent_sid());

        mergeInfo(o,order);

        o.setStatus(OrderStatus.RENTING.getStatus());

        if ( orderMapper.updateCostAndStatus(order) == 1 && vehicleService.updateVehicleById(vehicle) == 1 )
            return HttpStatus.OK;
        else return HttpStatus.ERROR;
    }

    @Override
    public int drawBackOrder(long id, Order order) {

        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        if ( o.getStatus() != OrderStatus.RENTING.getStatus() ) {
            logger.warn("the order's status is not renting - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        o.setRend(order.getRend());
        o.setRreturn_sid(order.getRreturn_sid());

        mergeInfo(o,order);

        o.setStatus(OrderStatus.DRAWBACK.getStatus());

        Vehicle vehicle = vehicleService.getVehicleById(o.getVid());
        vehicle.setSid(order.getRreturn_sid());
        vehicle.setStatus(VehicleStatus.OK.getStatus());
        vehicleService.updateVehicleById(vehicle);

        if ( orderMapper.updateCostAndStatus(o) == 1 ) return HttpStatus.OK;
        else return HttpStatus.ERROR;
        //TODO: 退款线程
    }

    @Override
    public int finishedOrder(long id, Order order) {

        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        if ( o.getStatus() != OrderStatus.DRAWBACK.getStatus() ) {
            logger.warn("the order's status is not renting - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        mergeInfo(o,order);
        o.setStatus(OrderStatus.DRAWBACK.getStatus());

        if ( orderMapper.updateCostAndStatus(o) == 1 ) return  HttpStatus.OK;
        else return HttpStatus.ERROR;
    }

    @Override
    public int cancleOrder(long id, Order order) {
        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        // TODO: 退款操作

        mergeInfo(o,order);
        o.setStatus(OrderStatus.CANCLED.getStatus());
        if ( orderMapper.updateCostInfoAndStatus(o) == 1 ) return HttpStatus.OK;
        else return HttpStatus.ERROR;
    }
}
