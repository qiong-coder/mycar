package com.mycar.mapper;

import com.mycar.model.Order;
import com.mycar.utils.OrderStatus;

import java.util.List;

/**
 * Created by stupid-coder on 7/17/17.
 */
public interface OrderMapper {

    Order getById(Long id);

    List<Order> getByStatus(OrderStatus status);

    int insertOrder(Order order);

}
