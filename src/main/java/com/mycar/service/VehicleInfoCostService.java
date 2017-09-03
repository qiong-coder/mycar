package com.mycar.service;


import com.alibaba.fastjson.JSONObject;
import com.mycar.model.VehicleInfoCost;

import java.sql.Timestamp;


/**
 * Created by qiong-coder on 8/1/17.
 */
public interface VehicleInfoCostService {

    VehicleInfoCost getVehicleInfoCostById(long viid);

    JSONObject getVehicleInfoCostInfo(long viid, Timestamp begin, Timestamp end);

    int update(VehicleInfoCost vehicleInfoCost);

//    int updateDayCosts(long viid, VehicleInfoCost vehicleInfoCost);
//
//    int updateInsurance(long viid, int base_insurance, int free_insurance);

    int insertDefaultVehicleInfoCost(long viid, int base_insurance, int free_insurance, int day_cost);

}
