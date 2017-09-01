package com.mycar.service.impl;

import com.mycar.mapper.VehicleInfoMapper;
import com.mycar.mapper.VehicleMapper;
import com.mycar.model.Order;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.service.VehicleService;
import com.mycar.utils.TimeUtils;
import com.mycar.utils.VehicleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by stupid-coder on 7/15/17.
 */
@Service("VehicleService")
public class VehicleServiceImpl implements VehicleService {

    private static Logger logger = LoggerFactory.getLogger(VehicleService.class);

    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;
    @Autowired
    private VehicleInfoCostService vehicleInfoCostService;

    @Override
    public Vehicle getVehicleById(long id) {
        Vehicle vehicle = vehicleMapper.getById(id);
        if ( vehicle == null ) logger.error("failure to get the vehicle - {}", id);
        return vehicle;
    }

    @Override
    public List<Vehicle> getAllVehiclesByViidAndStatusAndSid(long viid, int status) {
        List<Vehicle> vehicles = vehicleMapper.getByViidAndStatus(viid,status);
        if ( vehicles == null || vehicles.isEmpty() ) logger.warn("failure to get the vehicle by status and sid - viid:{}\tstatus:{}", viid,status);
        return vehicles;
    }

    @Override
    public Vehicle getVehicleByNumber(String number) {
        Vehicle vehicle = vehicleMapper.getByNumber(number);
        if ( vehicle == null ) logger.error("failure to get the vehicle - {}", number);
        return vehicle;
    }

    @Override
    public VehicleInfo getVehicleInfoById(long id) {
        VehicleInfo vehicleInfo = vehicleInfoMapper.getById(id);
        if ( vehicleInfo == null ) logger.error("failure to get the vehicle info - {}", id);
        return vehicleInfo;
    }

    @Override
    public VehicleInfo getVehicleInfoAndCostById(long id) {
        VehicleInfo vehicleInfo = getVehicleInfoById(id);
        if ( vehicleInfo != null ) {
            vehicleInfo.setCost(vehicleInfoCostService.getVehicleInfoCostById(vehicleInfo.getId()));
        }
        return vehicleInfo;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleMapper.getAll();
        if ( vehicles == null || vehicles.size() == 0 ) logger.error("failure to get the all vehicles");
        return vehicles;
    }

    @Override
    public List<Vehicle> getAllVehiclesByViid(long viid) {
        List<Vehicle> vehicles = vehicleMapper.getByViid(viid);
        if ( vehicles == null || vehicles.size() == 0 ) logger.error("failure to get the vehcile by viid - {}", viid);
        return vehicles;
    }

    @Override
    public List<VehicleInfo> getAllVehicleInfos() {
        List<VehicleInfo> vehicleInfos = vehicleInfoMapper.getAll();
        if ( vehicleInfos == null || vehicleInfos.size() == 0 ) logger.error("failure to get the all vehicle infos");
        for ( VehicleInfo vehicleInfo : vehicleInfos ) {
            vehicleInfo.setCost(vehicleInfoCostService.getVehicleInfoCostById(vehicleInfo.getId()));
            vehicleInfo.setVehicle_count(vehicleMapper.getVehicleCount(vehicleInfo.getId()));
        }
        return vehicleInfos;
    }

//    @Override
//    public VehicleInfo getVehicleInfoByIdAndTime(long id, Timestamp begin, Timestamp end) {
//        VehicleInfo vehicleInfo = getVehicleInfoById(id);
//        return vehicleInfo;
//    }

    @Override
    public List<VehicleInfo> getVehicleInfosByTime(Timestamp begin, Timestamp end) {

        List<Vehicle> vehicles = getAllVehicles();

        if ( vehicles == null || vehicles.isEmpty() ) return null;

        Set<Long> vidSet = new HashSet<>();
        for ( Vehicle vehicle : vehicles )
        {
            long iid = vehicle.getViid();
            if ( vidSet.contains(iid) ) continue;

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

        List<VehicleInfo> vehicleInfos = new ArrayList<>();
        for ( Long vid : vidSet )
        {
            VehicleInfo vehicleInfo = getVehicleInfoAndCostById(vid);
            if ( vehicleInfo != null ) vehicleInfos.add(vehicleInfo);
        }

        return vehicleInfos;
    }

    @Override
    public Map<Long, VehicleInfo> getVehicleInfosByOrders(List<Order> orders) {

        Map<Long, VehicleInfo> vehicleInfos =  new HashMap();
        for ( Order order : orders ) {
            long viid = order.getViid();
            if ( vehicleInfos.containsKey(viid) ) continue;
            else {
                VehicleInfo vehicleInfo = getVehicleInfoById(viid);
                if ( vehicleInfo == null ) continue;
                vehicleInfos.put(viid, vehicleInfo);
            }
        }
        return vehicleInfos;
    }

    @Override
    public Map<Long, Vehicle> getVehiclesByOrders(List<Order> orders) {
        Map<Long, Vehicle> vehicleMap = new HashMap<>();
        for ( Order order : orders ) {
            Long vid = order.getVid();
            if ( vid == null ) continue;
            else {
                Vehicle vehicle = getVehicleById(vid);
                if ( vehicle == null ) continue;
                vehicleMap.put(vid, vehicle);
            }
        }
        return vehicleMap;
    }

    @Override
    public int updateVehicleById(Vehicle vehicle) {
        return vehicleMapper.updateVehicleById(vehicle);
    }

    @Override
    public int updateVehicleDescription(long id, String description) {
        Vehicle vehicle = vehicleMapper.getById(id);
        if ( vehicle == null ) return -1;
        vehicle.setDescription(description);
        return vehicleMapper.updateVehicleDescription(vehicle);
    }

    @Override
    public int insertVechile(Vehicle vehicle) {
        if ( vehicleMapper.getByNumber(vehicle.getNumber()) !=  null ) return -1;
        return vehicleMapper.insertVehicle(vehicle);
    }

    @Override
    public int updateVehicleToDelete(long vid) {
        return vehicleMapper.updateVehicleToDelete(vid);
    }

    @Override
    public int insertVehicleInfo(VehicleInfo vehicleInfo) {
        return vehicleInfoMapper.insertVehicleInfo(vehicleInfo);
    }

    @Override
    public int updateVehicleInfo(long viid, VehicleInfo vehicleInfo) {
        vehicleInfo.setId(viid);
        return vehicleInfoMapper.updateVehicleInfo(vehicleInfo);
    }

    @Override
    public int updateVehicleInfoToDelete(long viid) {
        return vehicleInfoMapper.updateVehicleInfoToDelete(viid);
    }
}
