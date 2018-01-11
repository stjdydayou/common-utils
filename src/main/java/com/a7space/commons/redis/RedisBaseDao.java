package com.a7space.commons.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class RedisBaseDao {
    private Logger logger = LoggerFactory.getLogger(RedisBaseDao.class);

    @Autowired
    private JedisSentinelPool jedisSentinelPool;

    public JedisSentinelPool getJedisSentinelPool() {
        return jedisSentinelPool;
    }

    public <T> T redisOperate(IjedisOperate<T> jedisOperate) {
        T object = null;
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            logger.debug("redis:class=RedisBaseDao;Method=redisOperate;Host=" + jedis.getClient().getHost());
            object = jedisOperate.jedisOperate(jedis);
        } catch (Exception e) {
            logger.error("redis:class=RedisBaseDao;Method=redisOperate;ErrorMessage=" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return object;
    }
}
