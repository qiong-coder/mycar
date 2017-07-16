package com.mycar.model;

import java.sql.Timestamp;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class Order {

    private Long id;  // 订单ID
    private Long vid; // 车辆ID
    private Timestamp begin; // 借车时间
    private Timestamp end; // 还车时间
    private Timestamp real_end; // 实际还车时间
    private String rent_address;  // 借车地址
    private String return_address; // 还车地址
    private String name; // 租车人名字
    private Integer id_type; // 证件类型
    private String identity; // 证件号码
    private String phone; // 手机号码
    private String bill; // 发票
    private Double cost; // 最终的租车费用
    private Double base_insurance; // 最终的保险费
    private Double free_insurance; //
    private Double distance; // 人工填写里程
    private Double oil_cost; // 人工填写油价差
    private Double total_cost; // 最终的费用，人工填写
    private String decription;
    private Integer status; // 交易状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
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

    public Timestamp getReal_end() {
        return real_end;
    }

    public void setReal_end(Timestamp real_end) {
        this.real_end = real_end;
    }

    public String getRent_address() {
        return rent_address;
    }

    public void setRent_address(String rent_address) {
        this.rent_address = rent_address;
    }

    public String getReturn_address() {
        return return_address;
    }

    public void setReturn_address(String return_address) {
        this.return_address = return_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_type() {
        return id_type;
    }

    public void setId_type(Integer id_type) {
        this.id_type = id_type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getOil_cost() {
        return oil_cost;
    }

    public void setOil_cost(Double oil_cost) {
        this.oil_cost = oil_cost;
    }

    public Double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Double total_cost) {
        this.total_cost = total_cost;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", vid=" + vid +
                ", begin=" + begin +
                ", end=" + end +
                ", real_end=" + real_end +
                ", rent_address='" + rent_address + '\'' +
                ", return_address='" + return_address + '\'' +
                ", name='" + name + '\'' +
                ", id_type=" + id_type +
                ", identity='" + identity + '\'' +
                ", phone='" + phone + '\'' +
                ", bill='" + bill + '\'' +
                ", cost=" + cost +
                ", base_insurance=" + base_insurance +
                ", free_insurance=" + free_insurance +
                ", distance=" + distance +
                ", oil_cost=" + oil_cost +
                ", total_cost=" + total_cost +
                ", decription='" + decription + '\'' +
                ", status=" + status +
                '}';
    }
}
