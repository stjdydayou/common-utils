package com.a7space.commons.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.a7space.commons.utils.DateUtil;
import com.a7space.commons.utils.RandCodeUtil;

import redis.clients.jedis.Jedis;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Service("redisService")
public class RedisServiceImpl extends RedisBaseDao implements RedisService {

	protected static final String ZSET = "zset";

	protected static final String STRING = "string";

	protected static final String LIST = "list";

	protected static final String SET = "set";

	private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
	/**
	 * 通过key获取一个字符串
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Boolean exists(final String key) {
		return redisOperate(new IjedisOperate<Boolean>() {
			@Override
			public Boolean jedisOperate(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	@Override
	public Boolean exists(final String key,final int db){
		return redisOperate(new IjedisOperate<Boolean>() {
			@Override
			public Boolean jedisOperate(Jedis jedis) {
				jedis.select(db);
				return jedis.exists(key);
			}
		});
	}
	/**
	 * 删除一个key
	 *
	 * @param key
	 * @return 返回删除的条数 ; 0 未删除 ; null 没有这个key
	 */
	@Override
	public Long del(final String key) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}

	/**
	 * 设置key的过期时间(单位：秒)
	 *
	 * @param key
	 * @param seconds
	 * @return 返回1成功，0表示key已经设置过过期时间或者不存在
	 */
	@Override
	public Long expire(final String key, final int seconds) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	/**
	 * 获取key的剩余过期时间
	 *
	 * @param key
	 * @param key
	 * @return
	 */
	@Override
	public Long ttl(final String key) {

		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	/**
	 * 存储字符串
	 *
	 * @param key
	 * @param value
	 * @param expire
	 * @return 成功 OK ;
	 */
	@Override
	public String setString(final String key, final String value, final int expire) {
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				String result = jedis.set(key, value);
				if (expire > 0) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}
	@Override
	public String setString(final String key, final String value, final int db,final int expire){
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				jedis.select(db);
				String result = jedis.set(key, value);
				if (expire > 0) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}
	/**
	 * 通过key获取一个字符串
	 *
	 * @param key
	 * @return
	 */
	@Override
	public String getString(final String key) {
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	public String getString(final String key,final int db){
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				jedis.select(db);
				return jedis.get(key);
			}
		});
	}
	/**
	 * 往set中添加数据
	 *
	 * @param key
	 * @param expire
	 * @param members
	 * @return
	 */
	@Override
	public Long sadd(final String key, final int expire, final String... members) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				Long result = jedis.sadd(key, members);
				if (expire > 0) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}

	/**
	 * 返回set数据
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Set<String> smembers(final String key) {
		return redisOperate(new IjedisOperate<Set<String>>() {
			@Override
			public Set<String> jedisOperate(Jedis jedis) {
				return jedis.smembers(key);
			}
		});
	}

	/**
	 * 从key对应set中移除给定元素，成功返回1，如果member在集合中不存在或者key不存在返回0，如果key对应的不是set类型的值返回错误
	 *
	 * @param key
	 * @param members
	 * @return
	 */
	@Override
	public Long srem(final String key, final String... members) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				return jedis.srem(key, members);
			}
		});
	}

	/**
	 * Created on 2014-8-21
	 * <p>
	 * Discription:判断data是否在set中
	 * </p>
	 *
	 * @param key
	 * @param member
	 * @return
	 * @author:陈涛
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public Boolean sismember(final String key, final String member) {
		return redisOperate(new IjedisOperate<Boolean>() {
			@Override
			public Boolean jedisOperate(Jedis jedis) {
				return jedis.sismember(key, member);
			}
		});
	}

	/**
	 * Created on 2014-8-21
	 * <p>
	 * Discription:随机的移除并返回Set中的某一成员。
	 * 由于Set中元素的布局不受外部控制，因此无法像List那样确定哪个元素位于Set的头部或者尾部。
	 * </p>
	 *
	 * @param key
	 * @return
	 * @author:沈友军
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public String spop(final String key) {
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				return jedis.spop(key);
			}
		});
	}

	/**
	 * 查看长度，现以支持 list 、 zset 、set 类型
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Long len(final String key) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				Long sum = null;
				String type = jedis.type(key);
				if (ZSET.equals(type)) {
					sum = jedis.zcard(key);
				} else if (LIST.equals(type)) {
					sum = jedis.llen(key);
				} else if (SET.equals(type)) {
					sum = jedis.scard(key);
				} else {
					sum = null;
				}
				return sum;
			}
		});
	}

	/**
	 * 往对应list的头部添加字符串元素
	 *
	 * @param key
	 * @param expire
	 * @param strings
	 * @return
	 */
	@Override
	public Long lpush(final String key, final int expire, final String... strings) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				Long result = jedis.lpush(key, strings);
				if (expire > 0) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}

	/**
	 * 往对应list的尾部添加字符串元素
	 *
	 * @param key
	 * @param expire
	 * @param strings
	 * @return
	 */
	@Override
	public Long rpush(final String key, final int expire, final String... strings) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				Long result = jedis.rpush(key, strings);
				if (expire > 0) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}

	/**
	 * Created on 2014-6-23
	 * <p>
	 * Discription:从list的头部删除元素，并返回删除元素。如果key对应list不存在或者是空返回nil，
	 * 如果key对应值不是list返回错误
	 * </p>
	 *
	 * @param key
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public String lpop(final String key) {
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				return jedis.lpop(key);
			}
		});
	}

	/**
	 * Created on 2014-6-23
	 * <p>
	 * Discription:同上，但是从尾部删除
	 * </p>
	 *
	 * @param key
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public String rpop(final String key) {
		return redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	/**
	 * Created on 2014-6-23
	 * <p>
	 * Discription:从key对应list中删除count个和value相同的元素。count为0时候删除全部
	 * </p>
	 *
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public Long lrem(final String key, final long count, final String value) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				return jedis.lrem(key, count, value);
			}
		});
	}

	/**
	 * 返回list数据
	 *
	 * @param key
	 * @return
	 */
	@Override
	public List<String> lrange(final String key) {
		return redisOperate(new IjedisOperate<List<String>>() {
			@Override
			public List<String> jedisOperate(Jedis jedis) {
				return jedis.lrange(key, 0, -1);
			}
		});
	}

	@Override
	public List<String> lrange(final String key, final long start, final long end) {
		return redisOperate(new IjedisOperate<List<String>>() {
			@Override
			public List<String> jedisOperate(Jedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}

	@Override
	public Long incr(final String key, final int expire) {

		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				boolean needExpire = false;
				if (expire > 0) {
					needExpire = !jedis.exists(key);
				}
				Long result = jedis.incr(key);
				if (needExpire) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}

	@Override
	public Long incr(String key, int db, int expire) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				jedis.select(db);
				boolean needExpire = false;
				if (expire > 0) {
					needExpire = !jedis.exists(key);
				}
				Long result = jedis.incr(key);
				if (needExpire) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}

	@Override
	public Long decr(final String key, final int expire) {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				boolean needExpire = false;
				if (expire > 0) {
					needExpire = !jedis.exists(key);
				}
				Long result = jedis.decr(key);
				if (needExpire) {
					jedis.expire(key, expire);
				}
				return result;
			}
		});
	}

	@Override
	public Long generateOrderId() {
		return redisOperate(new IjedisOperate<Long>() {
			@Override
			public Long jedisOperate(Jedis jedis) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				String orderId = sdf.format(DateUtil.getCurrentDate());
				String redisKey = MessageFormat.format(RedisConstants.GENERATE_ORDER_INCR, orderId);

				Long incrValue = jedis.incr(redisKey);
				jedis.expire(redisKey, 600);
				Integer zeroCount = 6 - incrValue.toString().length();
				if (zeroCount > 0) {
					for (int i = 0; i < zeroCount; i++) {
						orderId = orderId + "0";
					}
				}
				orderId = orderId + incrValue;
				return Long.valueOf(orderId);
			}
		});
	}

	@Override
	public boolean redisLockOperate(String lockName, int lockTime, IredisLockOperate redisLockOperate) throws Exception {
		String lockId = redisOperate(new IjedisOperate<String>() {
			@Override
			public String jedisOperate(Jedis jedis) throws Exception {
				long end = System.currentTimeMillis() + lockTime * 1000;

				while (System.currentTimeMillis() < end) {
					String lockId = RandCodeUtil.getUUID();
					if (jedis.setnx(lockName, lockId) == 1) {
						jedis.expire(lockName, lockTime + 30);
						return lockId;
					}
				}
				return null;
			}
		});
		try {
			if (lockId != null) {
				redisLockOperate.lockOperate();
			} else {
				throw new TimeoutException("get lock time out");
			}
		} catch (Exception e) {
			logger.error("redisLockOperate error", e);
		} finally {
			redisOperate(new IjedisOperate<Boolean>() {
				@Override
				public Boolean jedisOperate(Jedis jedis) throws Exception {
					jedis.watch(lockName);
					if (lockId.equals(jedis.get(lockName))) {
						jedis.del(lockName);
					}
					jedis.unwatch();
					return true;
				}
			});
		}

		return true;
	}

}
