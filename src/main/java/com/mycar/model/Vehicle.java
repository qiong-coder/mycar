package com.mycar.model;

import java.sql.Timestamp;

/**
 * Created by stupid-coder on 7/15/17.
 */
public class Vehicle {

    private Long id;
    private Long iid; // VehicleInfo Id;
    private String number; // 车牌
    private Integer status; // 车辆状态
    private Timestamp begin; // 修车/租车开始时间
    private Timestamp end; // 修车/租车预计结束时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIid() {
        return iid;
    }

    public void setIid(Long iid) {
        this.iid = iid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", iid=" + iid +
                ", number='" + number + '\'' +
                ", status=" + status +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
