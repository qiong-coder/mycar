package com.mycar.logic;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.utils.TimeUtils;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class VehicleCost {

    public static String getPayCostInfo(int total_pay, String username)
    {
        JSONArray infos = new JSONArray();
        JSONObject info = new JSONObject();
        info.put("reason","租车预付");
        info.put("value", total_pay);
        info.put("operator", username);
        infos.add(info);
        return infos.toJSONString();
    }

    public static int getTotalCost(int day_cost, int base_insurance, int free_insurance, int days)
    {
        return (day_cost+base_insurance+free_insurance) * days;
    }

    public static String mergeInfo(String sinfo, String ninfo)
    {
        JSONArray sourceInfo = JSONArray.parseArray(sinfo);
        JSONArray newInfo = JSONArray.parseArray(ninfo);

        sourceInfo.addAll(newInfo);

        return sourceInfo.toJSONString();
    }
}
