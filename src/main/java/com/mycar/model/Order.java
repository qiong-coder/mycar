package com.mycar.model;

import java.sql.Timestamp;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class Order {
    private Long id;  // 订单Mysql-ID
    private String oid; // 订单ID
    private Long viid;
    private Long vid; // 车辆ID
    private Timestamp begin; // 借车时间
    private Timestamp end; // 还车时间
    private Long rent_store; // 借车门店编号
    private Long return_store; // 还车门店
    private String name; // 租车人名字
    private String identity; // 证件号码
    private String phone; // 手机号码
    private String bill; // 发票
    private Integer day_cost;
    private Integer base_insurance; // 最终的保险费
    private Integer free_insurance; //
    private String pay_info; // 用户付钱明细
    private Timestamp rbegin; // 实际还车时间
    private Timestamp rend;
    private Long rrent_store; // 实际借车门店
    private Long rreturn_store; // 实际还车门店
    private Double distance; // 人工填写里程
    private String cost_info; // 扣钱明细
    private Integer status; // 交易状态
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

    public Long getRent_store() {
        return rent_store;
    }

    public void setRent_store(Long rent_store) {
        this.rent_store = rent_store;
    }

    public Long getReturn_store() {
        return return_store;
    }

    public void setReturn_store(Long return_store) {
        this.return_store = return_store;
    }

    public Long getRrent_store() {
        return rrent_store;
    }

    public void setRrent_store(Long rrent_store) {
        this.rrent_store = rrent_store;
    }

    public Long getRreturn_store() {
        return rreturn_store;
    }

    public void setRreturn_store(Long rreturn_store) {
        this.rreturn_store = rreturn_store;
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

    public Integer getDay_cost() {
        return day_cost;
    }

    public void setDay_cost(Integer day_cost) {
        this.day_cost = day_cost;
    }

    public Integer getBase_insurance() {
        return base_insurance;
    }

    public void setBase_insurance(Integer base_insurance) {
        this.base_insurance = base_insurance;
    }

    public Integer getFree_insurance() {
        return free_insurance;
    }

    public void setFree_insurance(Integer free_insurance) {
        this.free_insurance = free_insurance;
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

    public Long getViid() {
        return viid;
    }

    public void setViid(Long viid) {
        this.viid = viid;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
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

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", viid=" + viid +
                ", vid=" + vid +
                ", begin=" + begin +
                ", end=" + end +
                ", rent_store=" + rent_store +
                ", return_store=" + return_store +
                ", name='" + name + '\'' +
                ", identity='" + identity + '\'' +
                ", phone='" + phone + '\'' +
                ", bill='" + bill + '\'' +
                ", day_cost=" + day_cost +
                ", base_insurance=" + base_insurance +
                ", free_insurance=" + free_insurance +
                ", pay_info='" + pay_info + '\'' +
                ", rbegin=" + rbegin +
                ", rend=" + rend +
                ", rrent_store=" + rrent_store +
                ", rreturn_store=" + rreturn_store +
                ", distance=" + distance +
                ", cost_info='" + cost_info + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                '}';
    }
}
