package com.mycar.mapper;


import com.mycar.model.VehicleInfoCost;


/**
 * Created by qiong-coder on 8/1/17.
 */
public interface VehicleInfoCostMapper {

    VehicleInfoCost get(long viid);

    int update(VehicleInfoCost vehicleInfoCost);

    int insert(VehicleInfoCost vehicleInfoCost);

}
