package com.a7space.commons.redis;

import java.util.List;
import java.util.Set;

public interface RedisService {
	/**
	 * 通过key获取一个字符串
	 * 
	 * @param key
	 * @return
	 */
	Boolean exists(final String key);

	Boolean exists(final String key, final int db);
	/**
	 * 删除一个key
	 * 
	 * @param key
	 * @return 返回删除的条数 ; 0 未删除 ; null 没有这个key
	 */
	Long del(final String key);

	/**
	 * 获取redis中所有相匹配的Key值
	 * 
	 * @param pattern
	 * @return
	 */
	//  Set<String> keys(final String pattern);

	/**
	 * 设置key的过期时间(单位：秒)
	 * 
	 * @param key
	 * @param seconds
	 * @return 返回1成功，0表示key已经设置过过期时间或者不存在
	 */
	Long expire(final String key, final int seconds);

	/**
	 * 获取key的剩余过期时间
	 * 
	 * @param key
	 * @param key
	 * @return
	 */
	Long ttl(final String key);

	/**
	 * 存储字符串
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return 成功 OK ;
	 */
	String setString(final String key, final String value, final int expire);

	String setString(final String key, final String value, final int db, final int expire);
	/**
	 * 通过key获取一个字符串
	 * 
	 * @param key
	 * @return
	 */
	String getString(final String key);

	String getString(final String key, final int db);

	/**
	 * 往set中添加数据
	 * 
	 * @param key
	 * @param expire
	 * @return
	 */
	Long sadd(final String key, final int expire, final String... members);

	/**
	 * 返回set数据
	 * 
	 * @param key
	 * @return
	 */
	Set<String> smembers(final String key);

	/**
	 * 从key对应set中移除给定元素，成功返回1，如果member在集合中不存在或者key不存在返回0，如果key对应的不是set类型的值返回错误
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	Long srem(final String key, final String... members);

	/**
	 * 
	 * Created on 2014-8-21
	 * <p>
	 * Discription:判断data是否在set中
	 * </p>
	 * 
	 * @author:陈涛
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param key
	 * @param member
	 * @return
	 */
	Boolean sismember(final String key, final String member);

	/**
	 * 
	 * Created on 2014-8-21
	 * <p>
	 * Discription:随机的移除并返回Set中的某一成员。
	 * 由于Set中元素的布局不受外部控制，因此无法像List那样确定哪个元素位于Set的头部或者尾部。
	 * </p>
	 * 
	 * @author:沈友军
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param key
	 * @return
	 */
	String spop(final String key);

	/**
	 * 查看长度，现以支持 list 、 zset 、set 类型
	 * 
	 * @param key
	 * @return
	 */
	Long len(final String key);

	/**
	 * 往对应list的头部添加字符串元素
	 * 
	 * @param key
	 * @param expire
	 * @return
	 */
	Long lpush(final String key, final int expire, final String... strings);

	/**
	 * 往对应list的尾部添加字符串元素
	 * 
	 * @param key
	 * @param itemKeys
	 * @return
	 */
	Long rpush(final String key, final int expire, final String... itemKeys);

	/**
	 * 
	 * Created on 2014-6-23
	 * <p>
	 * Discription:从list的头部删除元素，并返回删除元素。如果key对应list不存在或者是空返回nil，
	 * 如果key对应值不是list返回错误
	 * </p>
	 * 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param key
	 * @return
	 */
	String lpop(final String key);

	/**
	 * 
	 * Created on 2014-6-23
	 * <p>
	 * Discription:同上，但是从尾部删除
	 * </p>
	 * 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param key
	 * @return
	 */
	String rpop(final String key);

	/**
	 * 
	 * Created on 2014-6-23
	 * <p>
	 * Discription:从key对应list中删除count个和value相同的元素。count为0时候删除全部
	 * </p>
	 * 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	Long lrem(final String key, final long count, final String value);

	/**
	 * 返回list数据
	 * 
	 * @param key
	 * @return
	 */
	List<String> lrange(final String key);

	List<String> lrange(final String key, final long start, final long end);

	/**
	 * 原子性操作加一
	 * 
	 * @param key
	 * @return
	 */
	Long incr(final String key, final int expire);

	Long incr(final String key, final int db, final int expire);

	/**
	 * 原子性操作减一
	 * 
	 * @param key
	 * @return
	 */
	Long decr(final String key, final int expire);

	Long generateOrderId();

	/**
	 * redis 操作锁，用于一些操作只能是一个用户或者是一个线程执行
	 *
	 * @param lockKey
	 * @param lockTime
	 * @param redisLockOperate
	 * @return
	 */
	boolean redisLockOperate(String lockKey, int lockTime, IredisLockOperate redisLockOperate) throws Exception;
}
