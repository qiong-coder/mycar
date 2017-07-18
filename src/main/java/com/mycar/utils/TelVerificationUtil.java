package com.mycar.utils;

import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by apple on 2017/2/9.
 */
public class TelVerificationUtil {
    static Logger logger = LoggerFactory.getLogger(TelVerificationUtil.class);
    static String appKey = "";
    static String appSecret = "";
    static String templateId = "";

    public static String HttpClient(String url, Map<String,String> entitys) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        String curTime = String.valueOf(System.currentTimeMillis());

        httpPost.addHeader("AppKey",appKey);
        httpPost.addHeader("Nonce",curTime);
        httpPost.addHeader("CurTime",curTime);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        httpPost.addHeader("CheckSum", CheckSumBuilder.getCheckSum(appSecret,curTime,curTime));

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String,String> entry : entitys.entrySet() ) {
            nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        HttpResponse response = httpClient.execute(httpPost);

        return EntityUtils.toString(response.getEntity(),"utf-8");
    }
    public static String CommonInterface(String post_uri, Map<String,String> entitys) {
        try {
            String response = HttpClient(post_uri,entitys);
            JSONObject jo = JSONObject.parseObject(response);
            if ( "200".compareTo(jo.getString("code")) != 0 ) {
                logger.warn(String.format("Failure to %s - %s", entitys, response));
            } else {
                logger.info(String.format("Success to %s - %s",  entitys, response));
                return jo.getString("obj");
            }
        } catch ( Exception e ) {
            logger.warn(String.format("%s error - %s %s", entitys, e));
        }
        return null;
    }
    public static String SendCode(String phone){
        Map<String,String> entitys = new HashMap<String,String>();
        entitys.put("mobile",phone);
        entitys.put("templateid",templateId);
        return CommonInterface("https://api.netease.im/sms/sendcode.action",entitys);
    }
}
