package com.mycar.service.impl;

import com.mycar.mapper.OrderMapper;
import com.mycar.model.Order;
import com.mycar.service.OrderService;
import com.mycar.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stupid-coder on 7/18/17.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public int checkOrder(Long id) {
        return 0;
    }

    @Override
    public int rentOrder(Long id, String vehicle_number) {
        return 0;
    }

    @Override
    public int finishedOrder(Order order) {
        return 0;
    }

    @Override
    public int cancleOrder(Long id) {
        return 0;
    }

    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    @Override
    public List<Order> getOrderByStatus(OrderStatus status) {
        return null;
    }
}
