package com.mycar.logic;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.model.Order;
import com.mycar.utils.TimeUtils;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class VehicleCost {

    public static String getPayCostInfo(Double total_pay, String username)
    {
        JSONArray infos = new JSONArray();
        JSONObject info = new JSONObject();
        info.put("reason","租车预付");
        info.put("value", total_pay);
        info.put("operator", username);
        infos.add(info);
        return infos.toJSONString();
    }

    public static Double getTotalCost(Double day_cost, Double base_insurance, Double free_insurance, long days)
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
