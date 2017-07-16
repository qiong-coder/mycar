package com.mycar.model;

/**
 * Created by stupid-coder on 7/15/17.
 */
public class VehicleInfo {

    private Long id;
    private String name;  // 车系
    private String brand; // 品牌
    private Double displacement; // 排量
    private String gearbox; // 变速箱
    private String boxes; // 几厢车
    private Integer manned; // 乘员量
    private String description;
    private Double day_cost; // 日租赁费
    private Double base_insurance; // 基础保险费
    private Double free_insurance; // 免赔保险费
    private Double total_cost;

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

    public String getBoxes() {
        return boxes;
    }

    public void setBoxes(String boxes) {
        this.boxes = boxes;
    }

    public Integer getManned() {
        return manned;
    }

    public void setManned(Integer manned) {
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

    public Double getBase_insurance() {
        return base_insurance;
    }

    public void setBase_insurance(Double base_insurance) {
        this.base_insurance = base_insurance;
    }

    public Double getFree_insurance() {
        return free_insurance;
    }

    public void setFree_insurance(Double free_insurance) {
        this.free_insurance = free_insurance;
    }

    public Double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Double total_cost) {
        this.total_cost = total_cost;
    }

    @Override
    public String toString() {
        return "VehicleInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", displacement=" + displacement +
                ", gearbox='" + gearbox + '\'' +
                ", boxes='" + boxes + '\'' +
                ", manned=" + manned +
                ", description='" + description + '\'' +
                ", day_cost=" + day_cost +
                ", base_insurance=" + base_insurance +
                ", free_insurance=" + free_insurance +
                ", total_cost=" + total_cost +
                '}';
    }
}
