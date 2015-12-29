package org.springframework.amqp.helloworld.sync.annotation;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        Message msg = new Message("title", "body");

        amqpTemplate.convertAndSend(msg); //No other arguments provided will use the default settings.
    }
}
