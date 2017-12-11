package com.kidaekim.springtest.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessageApplication {
	
	@Bean
	MessageService mockMessageService() {
		return new MessageService() {
			@Override
			public String getMessage() {
				return "Hello Spring";
			}
		};
	}

	public static void main(String args[]) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MessageApplication.class, args);
		MessagePrinter messagePrinter = ctx.getBean(MessagePrinter.class);
		messagePrinter.printMessage();

//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MessageApplication.class);
//		MessagePrinter messagePrinter2 = context.getBean(MessagePrinter.class); 
//		messagePrinter2.printMessage();
//
//		context.close();
	}

}
