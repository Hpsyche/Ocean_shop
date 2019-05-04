package com.shop.jedis;

import java.util.Map;
import java.util.Set;

/**
 * @author Hpsyche
 */
public interface JedisClient {

	Set<String> hgetAll(String key);
	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);	
	Long hdel(String key, String... field);//删除hkey
	
}
