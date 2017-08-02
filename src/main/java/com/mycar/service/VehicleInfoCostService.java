package com.mycar.service;


import com.mycar.model.VehicleInfoCost;


/**
 * Created by qiong-coder on 8/1/17.
 */
public interface VehicleInfoCostService {

    VehicleInfoCost getVehicleInfoCostById(long viid);

    int updateDayCosts(long viid, VehicleInfoCost vehicleInfoCost);

    int updateInsurance(long viid, int base_insurance, int free_insurance);

}
