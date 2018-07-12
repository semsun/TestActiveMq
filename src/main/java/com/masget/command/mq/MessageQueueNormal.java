package com.masget.command.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class MessageQueueNormal implements MessageQueue {
	
	Logger log = Logger.getLogger(MessageQueueNormal.class);
	
	private Session session = null;
	private Destination destination = null;
	private Connection connection = null;
	
	private String queueName = null;
	
	private int deliveryMode = DeliveryMode.PERSISTENT;
	
	/**
	 * 创建消息队列
	 * @param session
	 * @param destination
	 * @throws JMSException 
	 */
	public MessageQueueNormal(String queueName, Connection connection) throws JMSException  {
		this.queueName = queueName;
		this.connection = connection;
		
//		this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		this.session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		this.destination = session.createQueue(queueName);
	}

	@Override
	public void sendMessage(String data) throws Exception {
		// TODO Auto-generated method stub
		this.sendMessage(data, this.deliveryMode);
	}

	@Override
	public void sendMessage(String data, int deliveryMode) throws Exception {
		// TODO Auto-generated method stub
		TextMessage message = this.session.createTextMessage(data);
		
		MessageProducer producer = this.session.createProducer(this.destination);
		producer.setDeliveryMode(deliveryMode);
		
		producer.send(message);
	}

	@Override
	public void setMessageListener(MessageListener listener) throws Exception {
		// TODO Auto-generated method stub
		this.session.createConsumer(this.destination).setMessageListener(listener);
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		this.closeSession();
		this.closeQueueConnection();
	}

	private void closeSession() {
		try {
			if (this.session != null) {
				this.session.close();
				this.session = null;
			}
		} catch (Exception e) {
			log.error("关闭消息队列session失败", e);
		}
	}

	private void closeQueueConnection() {
		try {
			if (this.connection != null) {
				this.connection.close();
				this.connection = null;
			}
		} catch (Exception e) {
			log.error("关闭消息队列连接失败", e);
		}
	}

}
