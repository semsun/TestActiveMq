package com.semsun.redis;

import redis.clients.jedis.Jedis;

import com.masget.command.redis.RedisCache;

public class TestRedis {

	public static void main(String args[]) {
		RedisCache cache = RedisCache.CreateCache("redis.properties");
		Jedis redis = cache.getCache();
		redis.set("testSet001", "001");
//		redis.expire("testSet001", 1);
		
		System.out.println(redis.get("testSet001"));
		redis.close();
		
		cache.closeRedisPool();
	}
}
