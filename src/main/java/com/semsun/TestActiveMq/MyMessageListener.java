package com.semsun.TestActiveMq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage msg = (TextMessage) message;
		
		try {
			System.out.println( String.format("%d %s", System.currentTimeMillis(), msg.getText()) );
			
			message.acknowledge();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
