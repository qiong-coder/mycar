package com.mycar.response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qixiang on 9/3/17.
 */
public class OrderSchedule {


    private Date begin;
    private Date end;
    private String name;
    private Long count;
    private Long stock;

    public OrderSchedule(String name, Long count, Long stock, Date begin) {
        this.begin = begin;
        this.name = name;
        this.count = count;
        this.stock = stock;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
