package com.mycar.logic;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.model.VehicleInfoCost;
import com.mycar.utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class VehicleCostLogic {

    public static String getPayInfo(int total_pay, String username) {
        JSONArray infos = new JSONArray();
        JSONObject info = new JSONObject();
        info.put("reason", "租车预付");
        info.put("value", total_pay);
        info.put("operator", username);
        infos.add(info);
        return infos.toJSONString();
    }

    public static JSONObject getCostInfoItem(String reason, int cost)
    {
        JSONObject dayCostInfoItem = new JSONObject();
        dayCostInfoItem.put("reason",reason);
        dayCostInfoItem.put("value",cost);
        return dayCostInfoItem;
    }

    public static int getPreCostInfo(VehicleInfoCost vehicleInfoCostInfo, Order order)
    {
        int sum = 0;
        int base_insurance = vehicleInfoCostInfo.getBase_insurance();
        int free_insurance = order.getIs_free_insurance() == null || order.getIs_free_insurance() == 0 ? 0 : vehicleInfoCostInfo.getFree_insurance();

        Calendar bcalender = Calendar.getInstance();
        bcalender.setTime(order.getBegin());
        long bhours = TimeUtils.GetHours(order.getBegin());
        long ehours = TimeUtils.GetHours(order.getEnd());


        JSONObject preCostInfo = new JSONObject();
        preCostInfo.put("base_insurance",base_insurance);
        preCostInfo.put("free_insurance",free_insurance);

        JSONArray dayCostInfos = new JSONArray();

        do {
            int cost = vehicleInfoCostInfo.getDay_cost(bcalender);
            dayCostInfos.add(getCostInfoItem(TimeUtils.GetDateFormat(bcalender),cost));
            sum += cost + base_insurance + free_insurance;
            bcalender.add(Calendar.DATE,1);
            bhours += 24;
        } while ( ehours - bhours >=6 );

        if ( ehours - bhours >= 5 ) {
            sum += 400;
            dayCostInfos.add(getCostInfoItem("超过5小时",400));
        } else if ( ehours - bhours >= 4 ) {
            sum += 300;
            dayCostInfos.add(getCostInfoItem("超过4小时",300));
        } else if ( ehours - bhours >= 3 ) {
            sum += 200;
            dayCostInfos.add(getCostInfoItem("超时3小时",200));
        } else if ( ehours - bhours >= 2 ) {
            sum += 100;
            dayCostInfos.add(getCostInfoItem("超时2小时",100));
        }

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
