package com.semsun.TestActiveMq;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.masget.command.mq.MessageQueueFactory;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="com.semsun")
@Controller
public class TestActiveMqApp
{
	
	@RequestMapping("test")
	@ResponseBody
	public String test() {
		System.out.println("test");
		
		return "test";
	}
	
	@RequestMapping("listenQueue")
	@ResponseBody
	public String listenQueue(String queueName) throws Exception {
		MessageQueueFactory.getMessageQueue(queueName, 0).setMessageListener(new MyMessageListener());
		return queueName;
	}
	
    public static void main( String[] args )
    {
    	MessageQueueFactory.createFactory("tcp://192.168.33.72:61616", "admin", "admin");
    	SpringApplication.run(TestActiveMqApp.class, args);
        System.out.println( "Hello World!" );
    }
    
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setPort(8801);
		factory.setSessionTimeout(10, TimeUnit.MINUTES);
//		factory.setContextPath("/oauth");
//		factory.addErrorPages( new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html") );
		return factory;
	}
}
