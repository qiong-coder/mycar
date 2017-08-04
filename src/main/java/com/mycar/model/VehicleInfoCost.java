package com.mycar.model;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by qixiang on 8/1/17.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class VehicleInfoCost {

    private Long viid;
    private Integer base_insurance;
    private Integer free_insurance;
    private String day_costs;
    private JSONArray day_costs_parse;

    public Long getViid() {
        return viid;
    }

    public void setViid(Long viid) {
        this.viid = viid;
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

    public String getDay_costs() {
        return day_costs;
    }

    public void setDay_costs(String day_costs) {
        this.day_costs = day_costs;
    }

    public JSONArray getDay_costs_parse() {
        return day_costs_parse;
    }

    public void setDay_costs_parse(JSONArray day_costs_parse) {
        this.day_costs_parse = day_costs_parse;
    }

    public int getDay_cost(Date date) {
        return day_costs_parse.getJSONArray(date.getMonth()).getInteger(date.getDate());
    }

    public void setDay_cost(Date date, int cost) { day_costs_parse.getJSONArray(date.getMonth()).set(date.getDate()-1,cost); }

    public void parseDay_costs_parse() {
        this.day_costs_parse = JSONArray.parseArray(day_costs);
        this.day_costs = null;
    }
    public void dumpDay_costs_parse() {
        this.day_costs = this.day_costs_parse.toJSONString();
        this.day_costs_parse = null;
    }
}
