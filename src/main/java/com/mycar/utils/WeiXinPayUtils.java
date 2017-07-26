package com.mycar.utils;

import com.github.wxpay.sdk.WXPay;
import com.mycar.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stupid-coder on 7/23/17.
 */
public class WeiXinPayUtils {

    private static Logger logger = LoggerFactory.getLogger(WeiXinPayUtils.class);

    @Autowired
    WeiXinPayConfig weiXinPayConfig;


    private boolean checkResult(Map<String,String> resp)
    {
        return resp != null && resp.containsKey("return_code") && resp.get("return_code").compareTo("SUCCESS") == 0 &&
                resp.containsKey("result_code") && resp.get("result_code").compareTo("SUCCESS") == 0;
    }

    public String getPayUrl(Order oid, String total_cost, String cost_detail, String client_ip)
    {
        WXPay pay = new WXPay(weiXinPayConfig);

        Map<String,String> data = new HashMap<String,String>();

        data.put("body",              "mycar - 租车服务");
        data.put("out_trade_no",      oid.getId().toString());
        data.put("detail",            cost_detail);
        data.put("total_fee",         total_cost);
        data.put("spbill_create_ip",  client_ip);
        data.put("notify_url",        ""); // TODO: notify url
        data.put("trade_type",        "NATIVE");
        data.put("time_start",        TimeUtils.GetSampleTime(0L));
        data.put("time_expire",       TimeUtils.GetSampleTime(1200L)); // 20分钟

        Map<String,String> resp;
        try {
            resp = pay.unifiedOrder(data);
        } catch (Exception e) {
            logger.error("failure to get pay url - {}", e);
            return null;
        }

        if ( !checkResult(resp) || resp.get("trade_type").compareTo("NATIVE") != 0 || !resp.containsKey("code_url") ) {
            logger.error("failure to get the pay url - req:{}\tresp:{}", data.toString(), resp.toString());
            return null;
        }

        return resp.get("code_url");
    }

    public boolean checkPay(long oid)
    {
        WXPay pay = new WXPay(weiXinPayConfig);

        Map<String,String> data = new HashMap<>();
        data.put("out_trade_no", Long.toString(oid));

        Map<String,String> resp;
        try {
            resp = pay.orderQuery(data);
        } catch ( Exception e ) {
            logger.error("failure to check the pay - {}", e);
            return false;
        }

        if ( !checkResult(resp) ) {
            logger.error("failure to check the pay - req:{}\tresp:{}", data.toString(), resp.toString());
            return false;
        }

        if ( resp.get("trade_status").compareTo("SUCCESS") == 0 ) {
            logger.info("success to check the pay - {}", resp.toString());
            return true;
        } else {
            logger.error("failiure to check the pay - resp:{}",resp.toString());
            return false;
        }
    }

}
