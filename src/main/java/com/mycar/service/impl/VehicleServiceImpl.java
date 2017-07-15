package com.mycar.service.impl;

import com.mycar.mapper.VehicleInfoMapper;
import com.mycar.mapper.VehicleMapper;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.service.VehicleService;
import com.mycar.utils.VehicleStatus;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/15/17.
 */
@Service("VehicleService")
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Override
    public Vehicle getVehicleById(int id) {
        return vehicleMapper.getById(id);
    }

    @Override
    public VehicleInfo getVehicleInfoById(int id) {
        return vehicleInfoMapper.getById(id);
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        return vehicleMapper.getAll();
    }

    @Override
    public List<VehicleInfo> getAllVehicleInfo() {
        return vehicleInfoMapper.getAll();
    }

    @Override
    public Map<VehicleInfo,Integer> getVehicleInfoByTime(Timestamp begin, Timestamp end) {
        List<Vehicle> vehicles = getAllVehicle();

        if ( vehicles == null || vehicles.isEmpty() ) return null;

        Map<Integer, Integer> infoStatic = new HashMap<>();
        for ( Vehicle vehicle : vehicles )
        {
            int iid = vehicle.getVinfo();
            VehicleStatus status = VehicleStatus.values()[vehicle.getStatus()];
            switch ( VehicleStatus.values()[vehicle.getStatus()] )
            {
                case OK: infoStatic.put(iid, infoStatic.getOrDefault(iid,0)); break;
                // TODO: 更精准的时间上控制
                case FIXING:
                case RENTING:
                case VALIDATE: break;
            }
        }

        if ( infoStatic.isEmpty() ) return null;

        Map<VehicleInfo,Integer> vehicleInfoIntegerMap = new HashMap<>();
        for ( Map.Entry<Integer,Integer> entry : infoStatic.entrySet() )
        {
            VehicleInfo vehicleInfo = getVehicleInfoById(entry.getKey());
            //if ( vehicleInfo == null ) continue;
            vehicleInfoIntegerMap.put(vehicleInfo,entry.getValue());
        }

        //if ( vehicleInfoIntegerMap.isEmpty() ) return null;
        return vehicleInfoIntegerMap;
    }
}
