package com.mycar.service;

import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.model.OrderStatusCount;
import com.mycar.model.Vehicle;
import com.mycar.utils.OrderStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/17/17.
 */
public interface OrderService {

    Order getOrderById(long id);

    List<Order> getOrdersByStatus(int status);

    List<OrderStatusCount> getOrdersNumberByStatus();

    List<Order> getOrdersByIdentityAndPhone(String identity, String phone);

    JSONObject getOrdersAndVehicleInfosByStatus(int stauts);

    JSONObject getOrdersAndVechileInfosByIdentityAndPhone(String identity, String phone);

    JSONObject getOrderAndVehicleInfoByOrderId(long id);

    int insertOrder(long viid, Order order); // 用户第一次提交订单

    int checkOrder(long id); // 检测用户的订单是否已经交钱

    int rentOrder(long id, Order order, String number); // 用户提车

    int drawBackOrder(long id, Order order); // 用车完成，退款状态

    int finishedOrder(long id, Order order); // 最终完成订单

    int cancleOrder(long id, Order order); // 取消订单


}
