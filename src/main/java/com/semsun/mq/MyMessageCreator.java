package com.semsun.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class MyMessageCreator implements MessageCreator {
	
	public int n = 0;
    private static String str1 = "这个是第 ";
    private static String str2 = " 个测试消息！";
    private String str = "";

	public Message createMessage(Session paramSession) throws JMSException {
		// TODO Auto-generated method stub
		if (n == 9) {
	        //在这个例子中表示第9次调用时，发送结束消息
	        return paramSession.createTextMessage("end");
	    }
	
	    str = str1 + n + str2;
	    return paramSession.createTextMessage(str);
	}

}
