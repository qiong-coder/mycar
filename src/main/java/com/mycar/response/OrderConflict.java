package com.mycar.response;

import com.mycar.model.Order;

import java.util.List;

/**
 * Created by qixiang on 11/9/17.
 */
public class OrderConflict {

    private Long total;

    private Long used;

    private Long to_used;

    List<Order> orders;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getTo_used() {
        return to_used;
    }

    public void setTo_used(Long to_used) {
        this.to_used = to_used;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
