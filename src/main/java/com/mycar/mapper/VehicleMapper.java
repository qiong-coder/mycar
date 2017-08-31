package com.mycar.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by stupid-coder on 7/15/17.
 */
public interface VehicleMapper {

    Vehicle getById(long id);

    List<Vehicle> getByViid(@Param("viid") long viid);

    List<Vehicle> getByViidAndStatus(@Param("viid") long viid, @Param("status") int status);

    int getVehicleCount(@Param("viid") long viid);

    Vehicle getByNumber(String number);

    List<Vehicle> getAll();

    int updateVehicleById(Vehicle vehicle);

    int updateVehicleDescription(Vehicle vehicle);

    int insertVehicle(Vehicle vehicle);

    int updateVehicleToDelete(long id);

}
