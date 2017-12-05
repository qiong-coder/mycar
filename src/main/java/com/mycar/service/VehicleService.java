package com.mycar.service;

import com.mycar.model.Order;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.response.VehicleInfoCount;

import javax.servlet.http.Part;
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

    VehicleInfo getVehicleInfoAndCostById(long id);

    List<Vehicle> getAllVehicles(Integer is_delete);

    List<Vehicle> getAllVehiclesByViid(Long viid, Integer is_delete);

    List<Vehicle> getAllVehiclesByViidAndStatusAndSid(long viid, int status);

    List<VehicleInfo> getAllVehicleInfos(Integer is_delete);

    //VehicleInfo getVehicleInfoByIdAndTime(long id, Timestamp begin, Timestamp end);

    List<Vehicle> getVehicleByTime(Long viid, Timestamp begin, Timestamp end);

    List<VehicleInfo> getVehicleInfosByTime(Timestamp begin, Timestamp end);

    Map<Long, VehicleInfo> getVehicleInfosByOrders(List<Order> orders);

    Map<Long, Vehicle> getVehiclesByOrders(List<Order> orders);

    int updateVehicleById(Vehicle vehicle);

    int updateVehicleDescription(long id, String description);

    int insertVechile(Vehicle vehicle);

    int delete(long vid, long force);

    int insertVehicleInfo(VehicleInfo vehicleInfo, Part attachment);

    int updateVehicleInfo(long viid, VehicleInfo vehicleInfo, Part attachment);

    int updateVehicleInfoToDelete(long viid);

    Map<Long, VehicleInfoCount> getVehicleCount(Long viid, Integer is_delete);
}
