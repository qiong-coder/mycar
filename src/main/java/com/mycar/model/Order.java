package com.mycar.model;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

/**
 * Created by stupid-coder on 7/16/17.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Order {
    private Long id;  // 订单Mysql-ID
    private String oid; // 订单ID
    private Long viid;
    private Long vid; // 车辆ID
    private Timestamp begin; // 借车时间
    private Timestamp end; // 还车时间
    private Long rent_sid; // 借车门店编号
    private Long return_sid; // 还车门店
    private String name; // 租车人名字
    private String identity; // 证件号码
    private String phone; // 手机号码
    private String bill; // 发票
    private String pre_cost; // 租车的基本价格信息
    private String pay_info; // 用户付钱明细
    private Timestamp rbegin; // 实际还车时间
    private Timestamp rend;
    private Long rrent_sid; // 实际借车门店
    private Long rreturn_sid; // 实际还车门店
    private Long distance;
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

    public Long getRent_sid() {
        return rent_sid;
    }

    public void setRent_sid(Long rent_sid) {
        this.rent_sid = rent_sid;
    }

    public Long getReturn_sid() {
        return return_sid;
    }

    public void setReturn_sid(Long return_sid) {
        this.return_sid = return_sid;
    }

    public Long getRrent_sid() {
        return rrent_sid;
    }

    public void setRrent_sid(Long rrent_sid) {
        this.rrent_sid = rrent_sid;
    }

    public Long getRreturn_sid() {
        return rreturn_sid;
    }

    public void setRreturn_sid(Long rreturn_sid) {
        this.rreturn_sid = rreturn_sid;
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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPre_cost() {
        return pre_cost;
    }

    public void setPre_cost(String pre_cost) {
        this.pre_cost = pre_cost;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
