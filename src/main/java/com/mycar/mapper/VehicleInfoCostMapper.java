package com.mycar.mapper;


import com.mycar.model.VehicleInfoCost;


/**
 * Created by qiong-coder on 8/1/17.
 */
public interface VehicleInfoCostMapper {

    VehicleInfoCost getVehicleInfoCostById(long viid);

    VehicleInfoCost getVehicleInfoCostByIdWithoutDayCost(long viid);

    int updateDayCost(VehicleInfoCost vehicleInfoCost);

    int updateInsurance(VehicleInfoCost vehicleInfoCost);

    int insertDefaultVehicleInfoCost(VehicleInfoCost vehicleInfoCost);

}
