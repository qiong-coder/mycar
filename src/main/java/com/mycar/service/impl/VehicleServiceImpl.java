package com.mycar.service.impl;

import com.mycar.logic.VehicleCost;
import com.mycar.mapper.VehicleInfoMapper;
import com.mycar.mapper.VehicleMapper;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.service.VehicleService;
import com.mycar.utils.TimeUtils;
import com.mycar.utils.VehicleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

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
    public Vehicle getVehicleById(long id) {
        return vehicleMapper.getById(id);
    }

    @Override
    public Vehicle getVehicleByNumber(String number) {
        return  vehicleMapper.getByNumber(number);
    }

    @Override
    public VehicleInfo getVehicleInfoById(long id) {
        return vehicleInfoMapper.getById(id);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleMapper.getAll();
    }

    @Override
    public List<VehicleInfo> getAllVehicleInfos() {
        return vehicleInfoMapper.getAll();
    }

    @Override
    public VehicleInfo getVehicleInfoByIdAndTime(long id, Timestamp begin, Timestamp end) {
        VehicleInfo vehicleInfo = getVehicleInfoById(id);
        return vehicleInfo;
    }

    @Override
    public List<VehicleInfo> getVehicleInfosByTime(Timestamp begin, Timestamp end) {
        List<Vehicle> vehicles = getAllVehicles();

        if ( vehicles == null || vehicles.isEmpty() ) return null;

        Set<Long> vidSet = new HashSet<>();
        for ( Vehicle vehicle : vehicles )
        {
            long iid = vehicle.getIid();
            VehicleStatus status = VehicleStatus.values()[vehicle.getStatus()];
            switch ( status )
            {
                case OK: vidSet.add(iid); break;
                case FIXING:
                case RENTING:
                    if ( !TimeUtils.TimeInteraction(begin,end,vehicle.getBegin(),vehicle.getEnd()) )
                        vidSet.add(iid); break;
                case VALIDATE: break;
            }
        }

        // TODO: 未来的订单影响

        if ( vidSet.isEmpty() ) return null;

        long days = TimeUtils.TimeDiff(end,begin);

        List<VehicleInfo> vehicleInfos = new ArrayList<>();
        for ( Long vid : vidSet )
        {
            VehicleInfo vehicleInfo = getVehicleInfoById(vid);
            vehicleInfos.add(vehicleInfo);
        }

        //if ( vehicleInfoIntegerMap.isEmpty() ) return null;
        return vehicleInfos;
    }
}
