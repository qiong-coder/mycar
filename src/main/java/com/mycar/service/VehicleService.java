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

    Vehicle getVehicleById(long id);

    VehicleInfo getVehicleInfoById(long id);

    List<Vehicle> getAllVehicle();

    List<VehicleInfo> getAllVehicleInfo();

    VehicleInfo getVehicleInfoByIdAndTime(long id, Timestamp begin, Timestamp end);

    List<VehicleInfo> getVehicleInfoByTime(Timestamp begin, Timestamp end);
}
