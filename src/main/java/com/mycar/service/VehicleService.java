package com.mycar.service;

import com.mycar.model.Order;
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

    Vehicle getVehicleByNumber(String number);

    VehicleInfo getVehicleInfoById(long id);

    List<Vehicle> getAllVehicles();

    List<VehicleInfo> getAllVehicleInfos();

    VehicleInfo getVehicleInfoByIdAndTime(long id, Timestamp begin, Timestamp end);

    List<VehicleInfo> getVehicleInfosByTime(Timestamp begin, Timestamp end);

    Map<Long, VehicleInfo> getVehicleInfoByOrders(List<Order> orders);
}
