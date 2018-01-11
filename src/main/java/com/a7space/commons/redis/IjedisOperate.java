package com.a7space.commons.redis;

import redis.clients.jedis.Jedis;

public interface IjedisOperate<T> {
	T jedisOperate(Jedis jedis) throws Exception;
}
