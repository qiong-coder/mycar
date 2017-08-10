package com.mycar.mapper;

import com.mycar.model.Order;
import com.mycar.model.OrderStatusCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/17/17.
 */
public interface OrderMapper {

    Order getOrderById(Long id);

    List<Order> getOrdersByStatus(int status);

    int insertOrder(Order order);

    int updateStatus(Order order);

    int updateCostInfoAndStatus(Order order);

    int updateCostAndStatus(Order order);

    List<Order> getOrdersByIdentityAndPhone(@Param("identity") String identity, @Param("phone") String phone);

    List<OrderStatusCount> getOrdersNumberByStatus();
}
