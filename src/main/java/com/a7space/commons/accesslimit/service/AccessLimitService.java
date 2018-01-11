package com.a7space.commons.accesslimit.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a7space.commons.redis.RedisConstants;
import com.a7space.commons.redis.RedisService;

@Service
public class AccessLimitService {
	private static Logger logger=LoggerFactory.getLogger(AccessLimitService.class);
	
	@Autowired
	private RedisService redisService;
	

    public long addAccessCount(int seconds, String... keys) {
        // TODO Auto-generated method stub
        String userPageCount=buildKey(RedisConstants.REDIS_KEY_ACCESS_LIMIT_PAGE_COUNTS,keys);
        return redisService.incr(userPageCount, RedisConstants.REDIS_DB_WEBSITE,seconds);
    }

    public long getAccessCount(String... keys) {
        // TODO Auto-generated method stub
        String userPageCount=buildKey(RedisConstants.REDIS_KEY_ACCESS_LIMIT_PAGE_COUNTS,keys);
        if(redisService.exists(userPageCount, RedisConstants.REDIS_DB_WEBSITE)){
            try {
                String val=redisService.getString(userPageCount, RedisConstants.REDIS_DB_WEBSITE);
                return Long.parseLong(val);
            } catch (Exception e) {
                // TODO: handle exception
                return 0;
            }
        }
        return 0;
    }

    public void addLocked(int seconds, String... keys) {
        // TODO Auto-generated method stub
        String userPageLock=buildKey(RedisConstants.REDIS_KEY_ACCESS_LIMIT_PAGE_LOCKS,keys);
        redisService.setString(userPageLock, "1", RedisConstants.REDIS_DB_WEBSITE, seconds);
    }

    public boolean isLocked(String... keys) {
        // TODO Auto-generated method stub
        String userPageLock=buildKey(RedisConstants.REDIS_KEY_ACCESS_LIMIT_PAGE_LOCKS,keys);
        Boolean isLocked=redisService.exists(userPageLock, RedisConstants.REDIS_DB_WEBSITE);
        if(isLocked!=null && isLocked==true){
            return true;
        }
        return false;
    }

    private String buildKey(String prefix, String[] keys) {
        if (keys == null && prefix==null) {
            return null;
        }
        String resultKey = prefix;
        for (String key : keys) {
            resultKey += "_";
            resultKey += key;
        }
        return resultKey;
    }
}