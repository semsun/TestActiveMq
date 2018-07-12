package com.semsun.mq2;

import redis.clients.jedis.Jedis;

import com.masget.command.mq.MessageQueue;
import com.masget.command.mq.MessageQueueFactory;
import com.masget.command.redis.RedisCache;

public class MessageSender {
	
	public static void main(String args[]) throws Exception {
//		MessageQueueFactory.createFactory("tcp://192.168.33.5:61616", "admin", "password");
		MessageQueueFactory.createFactory("tcp://192.168.33.72:61616", "admin", "admin");
		MessageQueue queue = MessageQueueFactory.getMessageQueue("TEST.QUEUE?consumer.exclusive=true", 1);
//		MessageQueue queue = MessageQueueFactory.getMessageQueue("testQueue001", 1);
		
		RedisCache cache = RedisCache.CreateCache("redis.properties");
		Jedis redis = cache.getCache();
		
//		for( int i = 0; i < 100000; i++ ) {
//			String queueName = String.format("testQueue%d", i);
//			MessageQueue tmpQueue = MessageQueueFactory.getMessageQueue(queueName, 1);
//			tmpQueue.sendMessage(queueName);
//			tmpQueue.closeConnection();
//		}
		
		for( int i = 0; i < 2; i++ ) {
			System.out.println("Send Msg");
			queue.sendMessage("Test001_" + i);
//			redis.incr("MessageNum");
			Thread.sleep(1000);
		}
		
		queue.closeConnection();
	}

}
