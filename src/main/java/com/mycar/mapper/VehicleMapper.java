package com.mycar.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Vehicle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by stupid-coder on 7/15/17.
 */
public interface VehicleMapper {

    Vehicle getById(long id);

    List<Vehicle> getByViid(@Param("viid") long viid);

    List<Vehicle> getByViidAndStatusAndSid(@Param("viid") long viid, @Param("status") int status, @Param("sid") long sid);

    Vehicle getByNumber(String number);

    List<Vehicle> getAll();

    int updateVehicleById(Vehicle vehicle);
}
