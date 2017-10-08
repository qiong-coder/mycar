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

import static com.mycar.logic.VehicleCostLogic.getCostInfoItem;


/**
 * Created by qixiang on 8/1/17.
 */
public class VehicleInfoCostServiceImpl implements VehicleInfoCostService {

    private static Logger logger = LoggerFactory.getLogger(VehicleInfoCostService.class);

    @Autowired
    private VehicleInfoCostMapper vehicleInfoCostMapper;

    @Override
    public VehicleInfoCost getVehicleInfoCostById(long viid) {
        return vehicleInfoCostMapper.get(viid);
    }

    @Override
    public JSONObject getVehicleInfoCostInfo(long viid, Timestamp begin, Timestamp end) {
        VehicleInfoCost vehicleInfoCost = getVehicleInfoCostById(viid);
        if ( vehicleInfoCost == null ) return null;
        JSONObject cost_info = new JSONObject();
        cost_info.put("base_insurance",vehicleInfoCost.getBase_insurance());
        cost_info.put("free_insurance",vehicleInfoCost.getFree_insurance());

        JSONArray v_day_costs = JSONArray.parseArray(vehicleInfoCost.getDay_costs());
        JSONArray v_discounts = JSONArray.parseArray(vehicleInfoCost.getDiscounts());

        Calendar calendar_begin = Calendar.getInstance();
        calendar_begin.setTime(begin);
        long bhours = TimeUtils.GetHours(begin);
        long ehours = TimeUtils.GetHours(end);
        long total = 0;
        JSONObject day_costs = new JSONObject();
        JSONObject discounts = new JSONObject();
        do {
            int day_cost = VehicleInfoCost.getArrayValueByCalendar(v_day_costs, calendar_begin);
            int discount = VehicleInfoCost.getArrayValueByCalendar(v_discounts, calendar_begin);

            day_costs.put(TimeUtils.GetDateFormat(calendar_begin),day_cost);
            discounts.put(TimeUtils.GetDateFormat(calendar_begin),discount);

            total += day_cost*discount/100 + vehicleInfoCost.getBase_insurance() + vehicleInfoCost.getFree_insurance();
            calendar_begin.add(Calendar.DATE,1);
            bhours += 24;
        } while ( ehours - bhours >= 6 );

        JSONObject overtime = new JSONObject();
        if ( ehours - bhours >= 5 ) {
            total += 40000;
            overtime.put("5",40000);
        } else if ( ehours - bhours >= 4 ) {
            total += 30000;
            overtime.put("4",30000);
        } else if ( ehours - bhours >= 3 ) {
            total += 20000;
            overtime.put("3",20000);
        } else if ( ehours - bhours >= 2 ) {
            total += 10000;
            overtime.put("2",10000);
        }

        if ( !overtime.isEmpty() ) cost_info.put("overtime",overtime);
        cost_info.put("day_costs", day_costs);
        cost_info.put("discounts", discounts);
        cost_info.put("total_cost", total);
        return cost_info;
    }

    @Override
    public int update(VehicleInfoCost vehicleInfoCost) {
        return vehicleInfoCostMapper.update(vehicleInfoCost);
    }

    //    @Override
//    public int updateDayCosts(long viid, VehicleInfoCost vehicleInfoCost) {
//        vehicleInfoCost.dumpDay_costs_parse();
//        return vehicleInfoCostMapper.updateDayCost(vehicleInfoCost);
//    }
//
//    @Override
//    public int updateInsurance(long viid, int base_insurance, int free_insurance) {
//        VehicleInfoCost vehicleInfoCost = vehicleInfoCostMapper.getVehicleInfoCostByIdWithoutDayCost(viid);
//        if ( vehicleInfoCost == null ) return 0;
//        vehicleInfoCost.setBase_insurance(base_insurance);
//        vehicleInfoCost.setFree_insurance(free_insurance);
//        return vehicleInfoCostMapper.updateInsurance(vehicleInfoCost);
//    }

    private String createDefaultArray(int value)
    {
        JSONArray values = new JSONArray();
        for ( int i = 0; i < 12; ++ i ) {
            JSONArray month_values = new JSONArray();
            for ( int j = 0; j < 31; ++ j ) {
                month_values.add(value);
            }
            values.add(month_values);
        }
        return values.toJSONString();
    }

    @Override
    public int insertDefaultVehicleInfoCost(long viid, int base_insurance, int free_insurance, int day_cost, int discount) {
        if ( vehicleInfoCostMapper.get(viid) != null ) return -1;

        VehicleInfoCost vehicleInfoCost = new VehicleInfoCost();
        vehicleInfoCost.setViid(viid);
        vehicleInfoCost.setBase_insurance(base_insurance);
        vehicleInfoCost.setFree_insurance(free_insurance);
        vehicleInfoCost.setDay_costs(createDefaultArray(day_cost));
        vehicleInfoCost.setDiscounts(createDefaultArray(discount));

        return vehicleInfoCostMapper.insert(vehicleInfoCost);
    }
}
