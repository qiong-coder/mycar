package com.mycar.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by stupid-coder on 7/15/17.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class VehicleInfo {

    private Long id;
    private String name;  // 车系
    private String displacement; // 排量
    private String gearbox; // 变速箱
    private String boxes; // 几厢车
    private String manned; // 乘员量
    private Integer spare;
    private String description;
    private String picture;
    private VehicleInfoCost cost;
    private Integer is_delete;
    private Integer vehicle_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getBoxes() {
        return boxes;
    }

    public void setBoxes(String boxes) {
        this.boxes = boxes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getManned() {
        return manned;
    }

    public void setManned(String manned) {
        this.manned = manned;
    }

    public VehicleInfoCost getCost() {
        return cost;
    }

    public void setCost(VehicleInfoCost cost) {
        this.cost = cost;
    }

    public Integer getSpare() {
        return spare;
    }

    public void setSpare(Integer spare) {
        this.spare = spare;
    }

    public Integer getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }

    public Integer getVehicle_count() {
        return vehicle_count;
    }

    public void setVehicle_count(Integer vehicle_count) {
        this.vehicle_count = vehicle_count;
    }
}
