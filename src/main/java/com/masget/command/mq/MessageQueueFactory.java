package com.masget.command.mq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.Logger;

public class MessageQueueFactory {
	
	private final Logger log = Logger.getLogger(MessageQueueFactory.class);
	
	// 设置连接的最大连接数
	public final static int DEFAULT_MAX_CONNECTIONS = 5;
	private int maxConnections = DEFAULT_MAX_CONNECTIONS;
	// 设置每个连接中使用的最大活动会话数
	private int maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION;
	public final static int DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION = 300;
	// 线程池数量
	private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
	public final static int DEFAULT_THREAD_POOL_SIZE = 50;
	// 强制使用同步返回数据的格式
	private boolean useAsyncSendForJMS = DEFAULT_USE_ASYNC_SEND_FOR_JMS;
	public final static boolean DEFAULT_USE_ASYNC_SEND_FOR_JMS = true;
	// 是否持久化消息
	private boolean isPersistent = DEFAULT_IS_PERSISTENT;
	public final static boolean DEFAULT_IS_PERSISTENT = true;
	// 连接地址
	private String brokerUrl;
	private String userName;
	private String password;
	private ExecutorService threadPool;
	private PooledConnectionFactory connectionFactory;
	
	private static MessageQueueFactory instance = null;
	
	public static MessageQueueFactory createFactory(String brokerUrl, String userName, String password) {
		instance = new MessageQueueFactory(brokerUrl, userName, password);
		
		return instance;
	}
	
	public MessageQueueFactory(String brokerUrl, String userName, String password) {
		this(brokerUrl, userName, password, DEFAULT_MAX_CONNECTIONS,
				DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION,
				DEFAULT_THREAD_POOL_SIZE, DEFAULT_USE_ASYNC_SEND_FOR_JMS,
				DEFAULT_IS_PERSISTENT);
	}
	
	public MessageQueueFactory(String brokerUrl, String userName,
			String password, int maxConnections,
			int maximumActiveSessionPerConnection, int threadPoolSize,
			boolean useAsyncSendForJMS, boolean isPersistent) {
		
		this.useAsyncSendForJMS = useAsyncSendForJMS;
		this.isPersistent = isPersistent;
		this.brokerUrl = brokerUrl;
		this.userName = userName;
		this.password = password;
		this.maxConnections = maxConnections;
		this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
		this.threadPoolSize = threadPoolSize;
		init();
		
	}

	/**
	 * 初始化线程池
	 */
	private void init() {
		// 设置JAVA线程池
		this.threadPool = Executors.newFixedThreadPool(this.threadPoolSize);
		// ActiveMQ的连接工厂
		ActiveMQConnectionFactory actualConnectionFactory = new ActiveMQConnectionFactory(
				this.userName, this.password, this.brokerUrl);
		actualConnectionFactory.setUseAsyncSend(this.useAsyncSendForJMS);
		// Active中的连接池工厂
		this.connectionFactory = new PooledConnectionFactory(
				actualConnectionFactory);
		this.connectionFactory.setCreateConnectionOnStartup(true);
		this.connectionFactory.setReconnectOnException(true);
		this.connectionFactory.setMaxConnections(this.maxConnections);
		this.connectionFactory
				.setMaximumActiveSessionPerConnection(this.maximumActiveSessionPerConnection);
	}
	
	/**
	 * 获取消息队列
	 * @param queueName	队列名称
	 * @param type		队列类型，目前只有一种类型 为0
	 * @return
	 */
	public static MessageQueue getMessageQueue(String queueName, int type) {
		if( null == instance ) return null;
		return instance.createNormalMessageQueue(queueName);
	}
	
	/**
	 * 创建常用消息队列
	 * @param queueName	队列名称
	 * @return
	 */
	private MessageQueue createNormalMessageQueue(String queueName) {
		Connection connection = null;
		try {
			// 从连接池工厂中获取一个连接
			connection = this.connectionFactory.createConnection();
			connection.start();
			
			return new MessageQueueNormal(queueName, connection);
		} catch( Exception e ) {
			log.error(e.getMessage());
		}
		
		return null;
	}
}
