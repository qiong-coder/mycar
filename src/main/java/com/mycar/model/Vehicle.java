package com.mycar.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

/**
 * Created by stupid-coder on 7/15/17.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Vehicle {

    private Long id;
    private Long viid; // VehicleInfo id;
    private String number; // 车牌
    private String description; // 描述
    private Integer status; // 车辆状态
    private Timestamp begin; // 修车/租车开始时间
    private Timestamp end; // 修车/租车预计结束时间
    private Integer is_delete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViid() {
        return viid;
    }

    public void setViid(Long viid) {
        this.viid = viid;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }
}
