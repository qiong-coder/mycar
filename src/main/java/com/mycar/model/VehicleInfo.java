package com.mycar.model;

/**
 * Created by stupid-coder on 7/15/17.
 */
public class VehicleInfo {

    private int id;
    private String name;  // 车系
    private String brand; // 品牌
    private Double displacement; // 排量
    private String gearbox; // 变速箱
    private int manned; // 乘员量
    private String description;
    private Double day_cost;
    private Double min_cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Double displacement) {
        this.displacement = displacement;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public int getManned() {
        return manned;
    }

    public void setManned(int manned) {
        this.manned = manned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDay_cost() {
        return day_cost;
    }

    public void setDay_cost(Double day_cost) {
        this.day_cost = day_cost;
    }

    public Double getMin_cost() {
        return min_cost;
    }

    public void setMin_cost(Double min_cost) {
        this.min_cost = min_cost;
    }

    @Override
    public String toString() {
        return "VehicleInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", displacement=" + displacement +
                ", gearbox='" + gearbox + '\'' +
                ", manned=" + manned +
                ", description='" + description + '\'' +
                ", day_cost=" + day_cost +
                ", min_cost=" + min_cost +
                '}';
    }
}
