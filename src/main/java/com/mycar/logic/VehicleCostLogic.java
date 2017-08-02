package com.mycar.logic;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.model.VehicleInfoCost;

import java.util.Calendar;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class VehicleCostLogic {

    public static String getPayInfo(int total_pay, String username)
    {
        JSONArray infos = new JSONArray();
        JSONObject info = new JSONObject();
        info.put("reason","租车预付");
        info.put("value", total_pay);
        info.put("operator", username);
        infos.add(info);
        return infos.toJSONString();
    }

    public static int getPreCostInfo(VehicleInfoCost vehicleInfoCostInfo, Order order)
    {
        int sum = 0;
        int base_insurance = vehicleInfoCostInfo.getBase_insurance();
        int free_insurance = vehicleInfoCostInfo.getFree_insurance();
        JSONArray costInfo = new JSONArray();

        Calendar bclander = Calendar.getInstance();
        bclander.setTime(order.getBegin());
        Calendar ecalender = Calendar.getInstance();
        ecalender.setTime(order.getEnd());

        JSONObject preCostInfo = new JSONObject();
        preCostInfo.put("base_insurance",base_insurance);
        preCostInfo.put("free_insurance",free_insurance);
        JSONArray dayCostInfos = new JSONArray();
        do {
            JSONObject dayCostInfoItem = new JSONObject();
            dayCostInfoItem.put("reason",bclander.toString());
            int cost = vehicleInfoCostInfo.getDay_cost(bclander.getTime());
            dayCostInfoItem.put("value",cost);
            dayCostInfos.add(dayCostInfoItem);
            sum += cost + base_insurance + free_insurance;
            bclander.add(Calendar.DATE,1);
        } while (bclander.compareTo(ecalender) <= 0 );
        preCostInfo.put("day_costs", dayCostInfos);

        order.setPre_cost(preCostInfo.toJSONString());
        return sum;
    }

    public static String mergeInfo(String sinfo, String ninfo)
    {
        JSONArray sourceInfo = JSONArray.parseArray(sinfo);
        JSONArray newInfo = JSONArray.parseArray(ninfo);

        sourceInfo.addAll(newInfo);

        return sourceInfo.toJSONString();
    }
}
