package com.mycar.model;

import java.sql.Timestamp;

/**
 * Created by stupid-coder on 7/15/17.
 */
public class Vehicle {

    private int id;
    private int vinfo; // VehicleInfo Table Id;
    private String number; // 车牌
    private int status; // 车辆状态
    private Timestamp begin; // 修车/租车开始时间
    private Timestamp end; // 修车/租车预计结束时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVinfo() {
        return vinfo;
    }

    public void setVinfo(int vinfo) {
        this.vinfo = vinfo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getEnd() {
        return end;
    }
}
