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
    public String getPayUrl(Long oid, String client_ip) {

        Order order = orderService.getOrderById(oid);
        if ( order == null ) {
            logger.error("failure to find the order by id - {}", oid);
            return null;
        }

        Double total_cost = VehicleCost.getTotalCost(order.getDay_cost(),order.getBase_insurance(),order.getFree_insurance(),
                TimeUtils.TimeDiff(order.getBegin(),order.getEnd()));

        return weiXinPayUtils.getPayUrl(order,new Integer(total_cost.intValue()).toString(),"",client_ip);
    }

    @Override
    public boolean checkPay(Long oid) {
        return weiXinPayUtils.checkPay(oid);
    }
}
