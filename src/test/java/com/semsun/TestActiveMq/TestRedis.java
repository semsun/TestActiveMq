package com.semsun.TestActiveMq;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.masget.command.redis.RedisCache;

public class TestRedis {

	@Ignore
	@Test
	public void testGetEmpty() {
		RedisCache cache = RedisCache.CreateCache("redis.properties");
		String str = cache.getCache().get("NoKey");
		
		Assert.fail(str);
	}
}
