package com.mycar.logic;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by stupid-coder on 7/16/17.
 */
public class VehicleCost {

    public static Double getTotalCost(Double day_cost, Double base_insurance, Double free_insurance, Long days)
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
