package com.mycar.mapper;

import com.mycar.model.VehicleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by stupid-coder on 7/15/17.
         */
public interface VehicleInfoMapper {

    List<VehicleInfo> getAll();

    List<VehicleInfo> getByStatusAndSid(int status, long sid);

    VehicleInfo getById(long id);

    int insertVehicleInfo(VehicleInfo vehicleInfo);

    int updateVehicleInfo(VehicleInfo vehicleInfo);

    int updateVehicleInfoToDelete(@Param("id")long viid);
}
