package com.a7space.commons.authority;

import com.alibaba.fastjson.JSON;
import com.a7space.commons.redis.RedisService;
import com.a7space.commons.utils.ValidateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component("oauthService")
public class OauthServiceRedisImpl extends OauthService {

	@Autowired
	private RedisService redisService;

	private final String OAUTH_REDIS_KEY = "SYSTEM:OAUTHUSER:{0}";

	private final String OAUTH_AUTHORITY_REDIS_KEY = "SYSTEM:OAUTHUSER:AUTHORITY:{0}";

	private final int expire = 60 * 60 * 2;// 登录失效时间2小时

	@Override
	public void setAuth(String sid, OauthUser oauthUser) {
		String json = JSON.toJSONString(oauthUser);
		String redisKeySid = MessageFormat.format(OAUTH_REDIS_KEY, sid);
		this.redisService.setString(redisKeySid, json, expire);
	}

	@Override
	public OauthUser getOAuth(String sid) {
		if (!ValidateUtil.isEmpty(sid)) {
			String redisKeySid = MessageFormat.format(OAUTH_REDIS_KEY, sid);
			String json = this.redisService.getString(redisKeySid);
			if (json != null) {
				OauthUser oauthUser = JSON.parseObject(json, OauthUser.class);
				if (oauthUser != null && sid.equals(oauthUser.getSid())) {
					this.redisService.expire(redisKeySid, expire);
					return oauthUser;
				}
			}

			String redisAuthorityKey = MessageFormat.format(OAUTH_AUTHORITY_REDIS_KEY, sid);
			if (this.redisService.exists(redisAuthorityKey)){
				this.redisService.expire(redisAuthorityKey, expire);
			}
		}
		return null;
	}

	@Override
	public void setAuthorities(String sid, List<String> listAuthorities) {
		String[] arrayAuthorities = listAuthorities.toArray(new String[listAuthorities.size()]);
		String redisKey = MessageFormat.format(OAUTH_AUTHORITY_REDIS_KEY, sid);
		this.redisService.del(redisKey);
		if (listAuthorities!=null && listAuthorities.size()>0){
			this.redisService.sadd(redisKey, expire, arrayAuthorities);
		}
	}

	@Override
	public boolean hasAuthority(String sid, String authority) {
		String redisKey = MessageFormat.format(OAUTH_AUTHORITY_REDIS_KEY, sid);

		if (ValidateUtil.isEmpty(authority)) {
			return false;
		} else {
			authority = authority.trim();
		}

		boolean result = this.redisService.sismember(redisKey, authority);
		if (result) {
			this.redisService.expire(redisKey, expire);
		}
		return result;
	}


	@Override
	public void destroy(String sid) {
		//销毁登录信息
		this.redisService.del(MessageFormat.format(OAUTH_REDIS_KEY, sid));

		//销毁权限信息
		String redisKey = MessageFormat.format(OAUTH_AUTHORITY_REDIS_KEY, sid);
		this.redisService.del(redisKey);
	}
}
