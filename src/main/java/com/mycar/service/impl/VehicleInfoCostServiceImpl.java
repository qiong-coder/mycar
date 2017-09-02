package com.mycar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.mapper.VehicleInfoCostMapper;
import com.mycar.model.VehicleInfoCost;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;


/**
 * Created by qixiang on 8/1/17.
 */
public class VehicleInfoCostServiceImpl implements VehicleInfoCostService {

    private static Logger logger = LoggerFactory.getLogger(VehicleInfoCostService.class);

    @Autowired
    private VehicleInfoCostMapper vehicleInfoCostMapper;

    @Override
    public VehicleInfoCost getVehicleInfoCostById(long viid) {
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostMapper.getVehicleInfoCostById(viid);
        if ( vehicleInfoCost == null ) return null;
        vehicleInfoCost.parseDay_costs_parse();
        return vehicleInfoCost;
    }

    @Override
    public JSONObject getVehicleInfoCostInfo(long viid, Timestamp begin, Timestamp end) {
        VehicleInfoCost vehicleInfoCost = getVehicleInfoCostById(viid);
        if ( vehicleInfoCost == null ) return null;
        JSONObject cost_info = new JSONObject();
        cost_info.put("base_insurance",vehicleInfoCost.getBase_insurance());
        cost_info.put("free_insurance",vehicleInfoCost.getFree_insurance());

        Calendar calendar_begin = Calendar.getInstance();
        calendar_begin.setTime(begin);
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.setTime(end);

        JSONObject day_costs = new JSONObject();
        int total = 0;
        do {
            int day_cost = vehicleInfoCost.getDay_cost(calendar_begin);
            day_costs.put(TimeUtils.GetDateFormat(calendar_begin),day_cost);
            total += day_cost + vehicleInfoCost.getBase_insurance() + vehicleInfoCost.getFree_insurance();
            calendar_begin.add(Calendar.DATE,1);
        } while ( calendar_begin.compareTo(calendar_end) <= 0 );
        cost_info.put("day_costs", day_costs);
        cost_info.put("total_cost", total);
        return cost_info;
    }

    @Override
    public int updateDayCosts(long viid, VehicleInfoCost vehicleInfoCost) {
        vehicleInfoCost.dumpDay_costs_parse();
        return vehicleInfoCostMapper.updateDayCost(vehicleInfoCost);
    }

    @Override
    public int updateInsurance(long viid, int base_insurance, int free_insurance) {
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostMapper.getVehicleInfoCostByIdWithoutDayCost(viid);
        if ( vehicleInfoCost == null ) return 0;
        vehicleInfoCost.setBase_insurance(base_insurance);
        vehicleInfoCost.setFree_insurance(free_insurance);
        return vehicleInfoCostMapper.updateInsurance(vehicleInfoCost);
    }

    private JSONArray createDefaultDayCosts(int day_cost)
    {
        JSONArray day_costs = new JSONArray();
        for ( int i = 0; i < 12; ++ i ) {
            JSONArray month_costs = new JSONArray();
            for ( int j = 0; j < 31; ++ j ) {
                month_costs.add(day_cost);
            }
            day_costs.add(month_costs);
        }
        return day_costs;
    }

    @Override
    public int insertDefaultVehicleInfoCost(long viid, int base_insurance, int free_insurance, int day_cost) {
        if ( vehicleInfoCostMapper.getVehicleInfoCostById(viid) != null ) return -1;

        VehicleInfoCost vehicleInfoCost = new VehicleInfoCost();
        vehicleInfoCost.setViid(viid);
        vehicleInfoCost.setBase_insurance(base_insurance);
        vehicleInfoCost.setFree_insurance(free_insurance);
        vehicleInfoCost.setDay_costs_parse(createDefaultDayCosts(day_cost));

        return vehicleInfoCostMapper.insertDefaultVehicleInfoCost(vehicleInfoCost);
    }
}
