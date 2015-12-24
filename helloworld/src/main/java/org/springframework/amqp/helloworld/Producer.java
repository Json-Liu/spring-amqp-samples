package org.springframework.amqp.helloworld;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        Map<String, Object> msg = new HashMap<>();
        msg.put("title", "title");
        msg.put("body", "hello world");

        //amqpTemplate.convertAndSend("amq.topic", "route.finance.commission.dealed", msg);
        amqpTemplate.convertAndSend(msg); //No other arguments provided will use the default settings.
    }
}
