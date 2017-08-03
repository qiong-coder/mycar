package com.mycar.service.impl;

import com.mycar.mapper.VehicleInfoCostMapper;
import com.mycar.model.VehicleInfoCost;
import com.mycar.service.VehicleInfoCostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Created by qixiang on 8/1/17.
 */
public class VehicleInfoCostServiceImpl implements VehicleInfoCostService {

    private static Logger logger = LoggerFactory.getLogger(VehicleInfoCostService.class);

    @Autowired
    private VehicleInfoCostMapper vehicleInfoCostMapper;

    @Override
    public VehicleInfoCost getVehicleInfoCostById(long viid) {
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostMapper.getVehicleInfoCostById(viid);
        if ( vehicleInfoCost == null ) return null;

        vehicleInfoCost.parseDay_cost();
        return vehicleInfoCost;
    }

    @Override
    public int updateDayCosts(long viid, VehicleInfoCost vehicleInfoCost) {
//        if ( begin.compareTo(end) > 0 ) return 0;
//
//        VehicleInfoCost vehicleInfoCost = vehicleInfoCostMapper.getVehicleInfoCostById(viid);
//        if ( vehicleInfoCost == null ) return 0;
//
//        Calendar bcalendar = Calendar.getInstance();
//        bcalendar.setTime(begin);
//        Calendar ecalendar = Calendar.getInstance();
//        ecalendar.setTime(end);
//
//        do {
//            vehicleInfoCost.setDay_cost(bcalendar.getTime(),cost);
//            bcalendar.add(Calendar.DATE,1);
//        } while ( bcalendar.compareTo(ecalendar) <= 0 );

        return vehicleInfoCostMapper.updateDayCost(vehicleInfoCost);
    }

    @Override
    public int updateInsurance(long viid, int base_insurance, int free_insurance) {
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostMapper.getVehicleInfoCostByIdWithoutDayCost(viid);
        if ( vehicleInfoCost == null ) return 0;
        vehicleInfoCost.setBase_insurance(base_insurance);
        vehicleInfoCost.setFree_insurance(free_insurance);
        return vehicleInfoCostMapper.updateInsurance(vehicleInfoCost);
    }
}