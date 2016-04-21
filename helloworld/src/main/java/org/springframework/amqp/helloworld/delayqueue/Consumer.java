package org.springframework.amqp.helloworld.delayqueue;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Consumer {

	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ConsumerDelayConfiguration.class);
	}

}
