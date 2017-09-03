package com.mycar.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.response.VehicleCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/15/17.
 */
public interface VehicleMapper {

    Vehicle getById(long id);

    List<Vehicle> getByViid(@Param("viid") Long viid, @Param("is_delete") Integer is_delete);

    List<Vehicle> getByViidAndStatus(@Param("viid") Long viid, @Param("status") int status);

    //int getVehicleCount(@Param("viid") long viid);

    Vehicle getByNumber(String number);

    List<Vehicle> getAll(@Param("is_delete") Integer is_delete);

    int updateVehicleById(Vehicle vehicle);

    int updateVehicleDescription(Vehicle vehicle);

    int insertVehicle(Vehicle vehicle);

    int updateVehicleToDelete(Long id);

    List<VehicleCount> getVehicleCount(@Param("viid") Long viid, @Param("is_delete") Integer is_delete);
}
