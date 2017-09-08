package com.mycar.utils.impl;

import com.mycar.utils.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by qixiang on 7/18/17.
 */
public class CacheUtilsImpl implements CacheUtils {


    private Logger logger = LoggerFactory.getLogger(CacheUtils.class);


    @Resource
    JedisPool jedisPool;

    public Jedis getResource()
    {
        try {
            Jedis client = jedisPool.getResource();
            if ( client == null ) logger.warn("failure to get the jedis client");
            return client;
        } catch( Exception e ) {
            logger.warn("failure to get the jedis client - {}", e);
            return null;
        }
    }

    public void returnClient(Jedis client)
    {
        client.close();
    }

    @Override
    public void put(String key, String value, int timeout) {
        Jedis client = getResource();
        if ( client == null ) return;

        if ( client.set(key,value).compareToIgnoreCase("ok") == 0 ) {
            client.expire(key, timeout);
        } else {
            logger.warn("failure to set the redis key:{}\tvalue:{}",key,value);
        }
        returnClient(client);
    }

    @Override
    public String get(String key) {
        Jedis client = getResource();

        if ( client == null ) return null;

        String value = client.get(key);

        returnClient(client);

        return value;
    }

    @Override
    public void delete(String key) {
        Jedis client = getResource();

        if ( client == null ) return;

        client.del(key);

        returnClient(client);
    }
}
