package com.mycar.mapper;

import com.mycar.model.VehicleInfo;

import java.util.List;

/**
 * Created by stupid-coder on 7/15/17.
         */
public interface VehicleInfoMapper {

    List<VehicleInfo> getAll();

    List<VehicleInfo> getByStatusAndSid(int status, long sid);

    VehicleInfo getById(long id);

}
