package com.mycar.service;

import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/15/17.
 */
public interface VehicleService {

    Vehicle getVehicleById(int id);

    VehicleInfo getVehicleInfoById(int id);

    List<Vehicle> getAllVehicle();

    List<VehicleInfo> getAllVehicleInfo();

    Map<VehicleInfo,Integer> getVehicleInfoByTime(Timestamp begin, Timestamp end);
}
