package com.mycar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.logic.VehicleCost;
import com.mycar.mapper.OrderMapper;
import com.mycar.model.Order;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.service.OrderService;
import com.mycar.service.VehicleService;
import com.mycar.utils.HttpStatus;
import com.mycar.utils.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stupid-coder on 7/18/17.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private OrderMapper orderMapper;

    private static void mergeInfo(Order l, Order r)
    {
        if ( r.getPay_info() != null )
            l.setPay_info(VehicleCost.mergeInfo(l.getPay_info(),r.getPay_info()));

        if ( r.getCost_info() != null )
            l.setCost_info(VehicleCost.mergeInfo(l.getPay_info(),r.getCost_info()));
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
    public int insertOrder(long viid, Order order) {
        VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(viid);
        if ( vehicleInfo == null ) {
            logger.warn("failure to get the vehicle info by id - id:{}", viid);
            return HttpStatus.NO_VEHICLE_INFO;
        }

        order.setViid(viid);
        order.setDay_cost(vehicleInfo.getDay_cost());
        order.setBase_insurance(vehicleInfo.getBase_insurance());
        order.setFree_insurance(vehicleInfo.getFree_insurance());

        // TODO: 计算付款价格，启动对应的付款
        if ( orderMapper.insertOrder(order) == 1 )
            return HttpStatus.OK;
        else return HttpStatus.ERROR;
    }

    @Override
    public int checkOrder(long id) {
        Order order = getOrderById(id);
        if ( order == null ) return HttpStatus.NO_ORDER;

        OrderStatus status = OrderStatus.values()[order.getStatus()];

        if ( status.compareTo(OrderStatus.PENDING) == 0 ) return HttpStatus.OK;

        //TODO: 结算判断
        return HttpStatus.OK;
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

        mergeInfo(o,order);

        o.setStatus(OrderStatus.RENTING.getStatus());

        return HttpStatus.OK;
    }

    @Override
    public int drawBackOrder(long id, Order order) {

        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        if ( o.getStatus() != OrderStatus.RENTING.getStatus() ) {
            logger.warn("the order's status is not renting - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        mergeInfo(o,order);

        o.setStatus(OrderStatus.DRAWBACK.getStatus());

        if ( orderMapper.updateCostAndStatus(o) == 1 ) return HttpStatus.OK;
        else return HttpStatus.ERROR;
        // 单独的线程进行退款操作
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
    public int cancleOrder(long id) {
        Order order = getOrderById(id);

        if ( order == null ) return HttpStatus.NO_ORDER;

        // TODO: 退款操作
        order.setStatus(OrderStatus.CANCLED.getStatus());

        if ( orderMapper.updateStatus(order) == 1 ) return HttpStatus.OK;
        else return HttpStatus.ERROR;
    }
}
