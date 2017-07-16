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
// TODO: 更精准的时间上控制
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
    public VehicleInfo getVehicleInfoByIdAndTime(int id, Timestamp begin, Timestamp end) {
        VehicleInfo vehicleInfo = getVehicleInfoById(id);
        if ( vehicleInfo != null ) vehicleInfo.setTotal_cost(VehicleCost.GetTotalCost(vehicleInfo.getDay_cost(),
                vehicleInfo.getBase_insurance(),
                vehicleInfo.getFree_insurance(),
                TimeUtils.TimeDiff(begin,end)));
        return vehicleInfo;
    }

    @Override
    public Map<VehicleInfo,Integer> getVehicleInfoByTime(Timestamp begin, Timestamp end) {
        List<Vehicle> vehicles = getAllVehicle();

        if ( vehicles == null || vehicles.isEmpty() ) return null;

        Map<Integer, Integer> infoStatic = new HashMap<>();
        for ( Vehicle vehicle : vehicles )
        {
            int iid = vehicle.getIid();
            VehicleStatus status = VehicleStatus.values()[vehicle.getStatus()];
            switch ( status )
            {
                case OK: infoStatic.put(iid, infoStatic.getOrDefault(iid,0)); break;
                case FIXING:
                case RENTING:
                    if ( !TimeUtils.TimeInteraction(begin,end,vehicle.getBegin(),vehicle.getEnd()) )
                        infoStatic.put(iid, infoStatic.getOrDefault(iid,0)); break;
                case VALIDATE: break;
            }
        }

        // TODO: 未来的订单影响

        if ( infoStatic.isEmpty() ) return null;

        long days = TimeUtils.TimeDiff(end,begin);

        Map<VehicleInfo,Integer> vehicleInfoIntegerMap = new HashMap<>();
        for ( Map.Entry<Integer,Integer> entry : infoStatic.entrySet() )
        {
            VehicleInfo vehicleInfo = getVehicleInfoById(entry.getKey());
            vehicleInfo.setTotal_cost(
                    VehicleCost.GetTotalCost(vehicleInfo.getDay_cost(),
                            vehicleInfo.getBase_insurance(),
                            vehicleInfo.getFree_insurance(),
                            TimeUtils.TimeDiff(begin,end)));
            vehicleInfoIntegerMap.put(vehicleInfo,entry.getValue());
        }

        //if ( vehicleInfoIntegerMap.isEmpty() ) return null;
        return vehicleInfoIntegerMap;
    }
}
