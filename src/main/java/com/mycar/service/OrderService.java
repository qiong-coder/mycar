package com.mycar.service;

import com.mycar.model.Order;
import com.mycar.model.Vehicle;
import com.mycar.utils.OrderStatus;

import java.util.List;

/**
 * Created by stupid-coder on 7/17/17.
 */
public interface OrderService {

    int insertOrder(Order order); // 用户第一次提交订单

    int checkOrder(Long id); // 检测用户的订单是否已经交钱

    int rentOrder(Long id, String vehicle_number); // 用户提车

    int finishedOrder(Order order); // 最终完成订单

    int cancleOrder(Long id); // 取消订单

    Order getOrderById(Long id);

    List<Order> getOrderByStatus(OrderStatus status);
}
