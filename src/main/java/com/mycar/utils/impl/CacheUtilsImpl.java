package com.mycar.utils.impl;

import com.mycar.utils.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Created by qixiang on 7/18/17.
 */
public class CacheUtilsImpl implements CacheUtils {


    private Logger logger = LoggerFactory.getLogger(CacheUtils.class);


    @Resource
    ShardedJedisPool shardedJedisPool;

    public ShardedJedis getResource()
    {
        try {
            ShardedJedis jedis = shardedJedisPool.getResource();
            return jedis;
        } catch( Exception e ) {
            logger.warn("failure to get the jedis client - {}", e);
            return null;
        }
    }

    public void returnClient(ShardedJedis jedis)
    {
        jedis.close();
    }

    @Override
    public void put(String key, String value, int timeout) {
        ShardedJedis jedis = getResource();
        if ( jedis == null ) return;

        jedis.set(key,value);
        jedis.
    }

    @Override
    public String get(String key) {
        return null;
    }
}
