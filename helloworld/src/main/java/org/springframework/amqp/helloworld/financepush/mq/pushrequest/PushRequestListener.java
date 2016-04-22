package org.springframework.amqp.helloworld.financepush.mq.pushrequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.helloworld.financepush.mq.AbstractBaseListener;
import org.springframework.amqp.helloworld.sync.annotation.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongshuwei on 4/22/16.
 */
public class PushRequestListener extends AbstractBaseListener<Message> {

    @Override
    protected void onMessageInternal(Message message) {

        try {
            System.out.println(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq_push_request_consumer.xml");

    }
}
