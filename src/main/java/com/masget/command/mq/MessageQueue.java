package com.masget.command.mq;

import javax.jms.MessageListener;

public interface MessageQueue {
	
	/**
	 * 发送消息
	 * @param data
	 * @throws Exception
	 */
	public void sendMessage(String data) throws Exception;
	
	/**
	 * 指定分发模式发送消息
	 * @param data				消息数据
	 * @param deliveryMode		分发模式：持久：DeliveryMode.PERSISTENT，非持久：DeliveryMode.NON_PERSISTENT
	 * @throws Exception
	 */
	public void sendMessage(String data, int deliveryMode) throws Exception;
	
	/**
	 * 设置监听器
	 * @param listener
	 * @throws Exception
	 */
	public void setMessageListener(MessageListener listener) throws Exception;
	
	/**
	 * 关闭消息队列连接
	 */
	public void closeConnection();

}
