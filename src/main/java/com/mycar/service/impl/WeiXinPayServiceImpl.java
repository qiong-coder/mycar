package com.mycar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mycar.logic.VehicleCostLogic;
import com.mycar.model.Order;
import com.mycar.service.OrderService;
import com.mycar.service.WeiXinPayService;
import com.mycar.utils.WeiXinPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by stupid-coder on 7/23/17.
 */
public class WeiXinPayServiceImpl implements WeiXinPayService {

    private static Logger logger = LoggerFactory.getLogger(WeiXinPayService.class);
    private static WeiXinPayUtils weiXinPayUtils = new WeiXinPayUtils();

    @Autowired
    OrderService orderService;

    @Override
    public String getPayUrl(long oid, String client_ip) {

        Order order = orderService.getOrderById(oid);
        if ( order == null ) {
            logger.error("failure to find the order by id - {}", oid);
            return null;
        }

        int sum = 0;
        JSONObject pre_cost = JSONObject.parseObject(order.getPre_cost());
        int base_insurance = pre_cost.getInteger("base_insurance");
        int free_insurance = pre_cost.getInteger("free_insurance");
        JSONArray day_costs = pre_cost.getJSONArray("day_costs");
        for ( int i = 0; i <  day_costs.size(); ++ i) {
            sum += base_insurance + free_insurance + day_costs.getJSONObject(i).getInteger("value");
        }

        //return weiXinPayUtils.getPayUrl(order,total_cost.toString(),"",client_ip);
        return "http://weixin.pay/test";
    }

    @Override
    public boolean checkPay(long oid) {
        return true;
        //return weiXinPayUtils.checkPay(oid);
    }
}
