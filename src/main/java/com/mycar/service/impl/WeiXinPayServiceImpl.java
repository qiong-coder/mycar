package com.mycar.service.impl;

import com.mycar.logic.VehicleCost;
import com.mycar.model.Order;
import com.mycar.service.OrderService;
import com.mycar.service.WeiXinPayService;
import com.mycar.utils.TimeUtils;
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

        int days = TimeUtils.TimeDiff(order.getBegin(),order.getEnd());
        Integer total_cost = VehicleCost.getTotalCost(order.getDay_cost(),order.getBase_insurance(),order.getFree_insurance(), days);

        //return weiXinPayUtils.getPayUrl(order,total_cost.toString(),"",client_ip);
        return "http://weixin.pay/test";
    }

    @Override
    public boolean checkPay(long oid) {
        return true;
        //return weiXinPayUtils.checkPay(oid);
    }
}
