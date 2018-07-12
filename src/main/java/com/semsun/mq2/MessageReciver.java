package com.semsun.mq2;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import redis.clients.jedis.Jedis;

import com.masget.command.mq.MessageQueue;
import com.masget.command.mq.MessageQueueFactory;
import com.masget.command.redis.RedisCache;

public class MessageReciver implements MessageListener {

	public static void main(String args[]) throws Exception {
		
		RedisCache cache = RedisCache.CreateCache("redis.properties");
		
//		MessageQueueFactory.createFactory("tcp://192.168.33.5:61616", "admin", "password");
		MessageQueueFactory.createFactory("tcp://192.168.33.72:61616", "admin", "admin");
		MessageQueue queue = MessageQueueFactory.getMessageQueue("TEST.QUEUE?consumer.exclusive=true", 1);
//		MessageQueue queue = MessageQueueFactory.getMessageQueue("testQueue001", 1);
		
		queue.setMessageListener(new MessageReciver());
		
		System.in.read();
		
		queue.closeConnection();
		cache.closeRedisPool();
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage textMessage = (TextMessage) message;
		try {
//			String key = textMessage.getText();
			Jedis redis = RedisCache.getInstance().getCache();
//			redis.incr(key);
//			redis.decr("MessageNum");
			
			System.out.println(textMessage.getText());
			System.out.println(String.format("Time: %d", System.currentTimeMillis()));
			redis.close();
			
			Thread.sleep(3000);
			if( textMessage.getText().equals("Test001_0") )
				throw new Exception("Custom Error!");
			message.acknowledge();
			System.out.println(String.format("Message: %s OK", textMessage.getText()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
