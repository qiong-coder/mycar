package com.mycar.service.impl;

import com.mycar.mapper.OrderMapper;
import com.mycar.mapper.VehicleInfoMapper;
import com.mycar.mapper.VehicleMapper;
import com.mycar.model.Order;
import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.response.VehicleCount;
import com.mycar.response.VehicleInfoCount;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.service.VehicleService;
import com.mycar.utils.FileUploadUtils;
import com.mycar.utils.TimeUtils;
import com.mycar.utils.VehicleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
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
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Override
    public Vehicle getVehicleById(Long id) {
        Vehicle vehicle = vehicleMapper.getById(id);
        if ( vehicle == null ) logger.error("failure to get the vehicle - {}", id);
        return vehicle;
    }

    @Override
    public List<Vehicle> getAllVehiclesByViidAndStatusAndSid(Long viid, Integer status, Integer is_delete) {
        List<Vehicle> vehicles = vehicleMapper.getByViidAndStatus(viid,status,is_delete);
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
    public VehicleInfo getVehicleInfoById(Long id) {
        VehicleInfo vehicleInfo = vehicleInfoMapper.getById(id);
        if ( vehicleInfo == null ) logger.error("failure to get the vehicle info - {}", id);
        return vehicleInfo;
    }

    @Override
    public VehicleInfo getVehicleInfoAndCostById(Long id) {
        VehicleInfo vehicleInfo = getVehicleInfoById(id);
        if ( vehicleInfo != null ) {
            vehicleInfo.setCost(vehicleInfoCostService.getVehicleInfoCostById(vehicleInfo.getId()));
        }
        return vehicleInfo;
    }

    @Override
    public List<Vehicle> getAllVehicles(Integer is_delete) {
        List<Vehicle> vehicles = vehicleMapper.getAll(is_delete);
        if ( vehicles == null || vehicles.size() == 0 ) logger.error("failure to get the all vehicles");
        return vehicles;
    }

    @Override
    public List<Vehicle> getAllVehiclesByViid(Long viid, Integer is_delete) {
        List<Vehicle> vehicles = vehicleMapper.getByViid(viid,is_delete);
        if ( vehicles == null || vehicles.size() == 0 ) logger.error("failure to get the vehcile by viid - {}", viid);
        return vehicles;
    }

    @Override
    public List<VehicleInfo> getAllVehicleInfos(Integer is_delete) {
        List<VehicleInfo> vehicleInfos = vehicleInfoMapper.getAll(is_delete);
        if ( vehicleInfos == null || vehicleInfos.size() == 0 ) logger.error("failure to get the all vehicle infos");
        for ( VehicleInfo vehicleInfo : vehicleInfos ) {
            vehicleInfo.setCost(vehicleInfoCostService.getVehicleInfoCostById(vehicleInfo.getId()));
            List<VehicleCount> vehicleCounts = vehicleMapper.getVehicleCount(vehicleInfo.getId(),is_delete);
            for ( VehicleCount vehicleCount : vehicleCounts ) {
                if ( vehicleCount.getViid().compareTo(vehicleInfo.getId()) == 0 )
                    vehicleInfo.setVehicle_count(vehicleCount.getCount().intValue());
            }
        }
        return vehicleInfos;
    }

//    @Override
//    public VehicleInfo getVehicleInfoByIdAndTime(long id, Timestamp begin, Timestamp end) {
//        VehicleInfo vehicleInfo = getVehicleInfoById(id);
//        return vehicleInfo;
//    }


    @Override
    public List<Vehicle> getVehicleByTime(Long viid, Timestamp begin, Timestamp end) {

        List<Vehicle> vehicles = getAllVehiclesByViid(viid,0);

        for ( Iterator<Vehicle> iter = vehicles.iterator(); iter.hasNext(); ) {
            Vehicle vehicle = iter.next();
            if ( (vehicle.getStatus() == VehicleStatus.FIXING.getStatus() || vehicle.getStatus() == VehicleStatus.RENTING.getStatus() )
                    && TimeUtils.TimeInteraction(begin,end,vehicle.getBegin(),vehicle.getEnd()) ) {
                iter.remove();
            }
        }

        return vehicles;
    }

    @Override
    public List<VehicleInfo> getVehicleInfosByTime(Timestamp begin, Timestamp end) {

<<<<<<< HEAD
        List<Vehicle> vehicles = getAllVehicles(0);
=======
        List<Vehicle> vehicles = getVehicleByTime(null, begin, end);
>>>>>>> c36339e911c253d226227fa3f40607e451d13ae6

        if ( vehicles == null || vehicles.isEmpty() ) return null;

        Map<Long, Integer> free_counts = new HashMap<>();
        for ( Vehicle vehicle : vehicles ) {
            Long viid = vehicle.getViid();
            free_counts.put(viid,free_counts.getOrDefault(viid,0)+1);
        }

        List<Order> orders = orderMapper.getOrdersByScheduleInterval(null,begin,end);

        Map<Long, Integer> use_counts = new HashMap<>();
        if ( orders != null ) {
            for( Order order : orders ) {
                use_counts.put(order.getViid(), use_counts.getOrDefault(order.getViid(),0));
            }
        }

        List<VehicleInfo> vehicleInfos = new ArrayList<>();
        for (Map.Entry<Long,Integer> entry : free_counts.entrySet() )
        {
            Long viid = entry.getKey();
            VehicleInfo vehicleInfo = getVehicleInfoAndCostById(viid);
            if ( vehicleInfo != null ) {
                if ( entry.getValue() - use_counts.getOrDefault(viid, 0) - vehicleInfo.getSpare() > 0 ) {
                    vehicleInfo.setVehicle_count(entry.getValue() - use_counts.getOrDefault(viid, 0) - vehicleInfo.getSpare());
                    vehicleInfos.add(vehicleInfo);
                }
            }
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
    public int insertVehicleInfo(VehicleInfo vehicleInfo, Part attachment) {
        String filename = fileUploadUtils.save(attachment);
        if ( filename == null ) return 0;

        vehicleInfo.setPicture(filename);
        if ( vehicleInfoMapper.insertVehicleInfo(vehicleInfo) != 1 ) return 0;
        //TODO: defualt vehicle info cost
        return vehicleInfoCostService.insertDefaultVehicleInfoCost(vehicleInfo.getId(),10000,10000,10000);

    }

    @Override
    public int updateVehicleInfo(long viid, VehicleInfo vehicleInfo, Part attachment) {
        String filename = fileUploadUtils.save(attachment);
        if ( filename != null ) vehicleInfo.setPicture(filename);

        vehicleInfo.setId(viid);
        return vehicleInfoMapper.updateVehicleInfo(vehicleInfo);
    }

    @Override
    public int updateVehicleInfoToDelete(long viid) {
        return vehicleInfoMapper.updateVehicleInfoToDelete(viid);
    }

    @Override
    public Map<Long, VehicleInfoCount> getVehicleCount(Long viid, Integer is_delete) {
        List<VehicleCount> vehicleCounts = vehicleMapper.getVehicleCount(viid,is_delete);
        Map<Long, VehicleInfoCount> vehicleInfoCounts = new HashMap<>();
        for ( VehicleCount vehicleCount : vehicleCounts ) {
            VehicleInfoCount vehicleInfoCount = new VehicleInfoCount();
            VehicleInfo vehicleInfo = vehicleInfoMapper.getById(vehicleCount.getViid());
            if ( vehicleInfo != null ) {
                vehicleInfoCount.setViid(vehicleCount.getViid());
                vehicleInfoCount.setCount(vehicleCount.getCount());
                vehicleInfoCount.setSpare(vehicleInfo.getSpare());
                vehicleInfoCount.setName(vehicleInfo.getName());
            } else {
                continue;
            }
            vehicleInfoCounts.put(vehicleCount.getViid(),vehicleInfoCount);
        }
        return vehicleInfoCounts;
    }
}
