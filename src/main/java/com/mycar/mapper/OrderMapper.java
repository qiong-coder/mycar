package com.mycar.mapper;

import com.mycar.model.Order;

import java.util.List;

/**
 * Created by stupid-coder on 7/17/17.
 */
public interface OrderMapper {

    Order getOrderById(Long id);

    List<Order> getOrdersByStatus(int status);

    int insertOrder(Order order);

    int updateStatus(Order order);

    int updateCostAndStatus(Order order);

}
