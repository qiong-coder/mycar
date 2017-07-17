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
    private String rent_address;  // 借车地址
    private String return_address; // 还车地址
    private String name; // 租车人名字
    private String identity; // 证件号码
    private String phone; // 手机号码
    private String bill; // 发票
    private Double day_cost;
    private Double base_insurance; // 最终的保险费
    private Double free_insurance; //
    private Double paid; // 总共付了多少钱
    private String paid_info; // 具体付款明细
    private Timestamp rbegin; // 实际还车时间
    private Timestamp rend;
    private Double distance; // 人工填写里程
    private Double cost; // 最终需要扣除多少钱
    private String cost_info; // 扣钱密西
    private Integer status; // 交易状态
    private String operator; // 操作员
    private Timestamp create_time;

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

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public String getPaid_info() {
        return paid_info;
    }

    public void setPaid_info(String paid_info) {
        this.paid_info = paid_info;
    }

    public Timestamp getRbegin() {
        return rbegin;
    }

    public void setRbegin(Timestamp rbegin) {
        this.rbegin = rbegin;
    }

    public Timestamp getRend() {
        return rend;
    }

    public void setRend(Timestamp rend) {
        this.rend = rend;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCost_info() {
        return cost_info;
    }

    public void setCost_info(String cost_info) {
        this.cost_info = cost_info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", vid=" + vid +
                ", begin=" + begin +
                ", end=" + end +
                ", rent_address='" + rent_address + '\'' +
                ", return_address='" + return_address + '\'' +
                ", name='" + name + '\'' +
                ", identity='" + identity + '\'' +
                ", phone='" + phone + '\'' +
                ", bill='" + bill + '\'' +
                ", day_cost=" + day_cost +
                ", base_insurance=" + base_insurance +
                ", free_insurance=" + free_insurance +
                ", paid=" + paid +
                ", paid_info='" + paid_info + '\'' +
                ", rbegin=" + rbegin +
                ", rend=" + rend +
                ", distance=" + distance +
                ", cost=" + cost +
                ", cost_info='" + cost_info + '\'' +
                ", status=" + status +
                ", operator='" + operator + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
