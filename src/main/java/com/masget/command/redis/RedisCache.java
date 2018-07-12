package com.masget.command.redis;

import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisCache {

	private JedisPool mPool = null;
	
	public static RedisCache instance = null;
	
	public static RedisCache CreateCache(String propertieName) {
		instance = new RedisCache(propertieName);
		
		return instance;
	}

	public static RedisCache getInstance() {		
		return instance;
	}
	
	private RedisCache(String propertieName) {
		initRedis(propertieName);
	}
	
	/**
	 * 读取Redis配置
	 * @param propertieName	Redis配置文件名，Redis配置文件需要放在CLASS_PATH下
	 * @return
	 */
	private Properties getRedisProperties(String propertieName) {
        
        Properties properties = new Properties();
        try{
        	
	        InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertieName);
	        properties.load(in);
	        in.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
        return properties;
	}
	
	private void initRedis(String propertieName) {
		Properties properties = this.getRedisProperties(propertieName);
		
        int maxActive = Integer.parseInt( String.valueOf(properties.get("redis.pool.maxActive")) );
        int maxIdle = Integer.parseInt( String.valueOf(properties.get("redis.pool.maxIdle")) );
        int maxWait = Integer.parseInt( String.valueOf(properties.get("redis.pool.maxWait")) );
        boolean testOnBorrow = Boolean.parseBoolean( String.valueOf(properties.get("redis.pool.testOnBorrow")) );
        boolean testOnReturn = Boolean.parseBoolean( String.valueOf(properties.get("redis.pool.testOnReturn")) );
        
        String host = String.valueOf(properties.get("redis.ip"));
        int port = Integer.parseInt( String.valueOf(properties.get("redis.port")) );
        int database = Integer.parseInt( String.valueOf(properties.get("redis.database")) );
        
        String msg = String.format("maxActive:%d\nmaxIdle:%d\nmaxWait:%d\ntestOnBorrow:%b\ntestOnReturn:%b\nhost:%s\nport:%d\ndatabase:%d"
        		, maxActive, maxIdle, maxWait, testOnBorrow, testOnReturn, host, port, database);
        System.out.println( msg );
        JedisPoolConfig redisPoolConf = new JedisPoolConfig();
        redisPoolConf.setMaxTotal(maxActive);
        redisPoolConf.setMaxIdle(maxIdle);
        redisPoolConf.setMaxWaitMillis(maxWait);
        redisPoolConf.setTestOnReturn(testOnReturn);
        redisPoolConf.setTestOnBorrow(testOnBorrow);
        
        mPool = new JedisPool(redisPoolConf, host, port, Protocol.DEFAULT_TIMEOUT, null, database);
//      JedisPool pool = new JedisPool(redisPoolConf, host, port);
	}
	
	public Jedis getCache() {
		if( null == mPool ) return null;
		
		return mPool.getResource();
	}
	
	public void closeRedisPool() {
		if( null == mPool ) return;
		
		mPool.close();
	}
}
