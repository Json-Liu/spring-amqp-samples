package org.springframework.amqp.helloworld.async.xml;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.helloworld.sync.annotation.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerAndConsumer {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq.xml");
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);

        Message msg = new Message("title", "body");
        //It will use the json message converter to convert the POJO and send
        amqpTemplate.convertAndSend(msg);
    }

}
