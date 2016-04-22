package org.springframework.amqp.helloworld.financepush.mq.pushrequest;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.helloworld.sync.annotation.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongshuwei on 4/22/16.
 */
public class PushRequestProducer {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq_push_request_producer.xml");
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);

        Message msg = new Message("push request title", "push request body");
        //send to push request queue
        //amqpTemplate.convertAndSend("", "queue.push.request", msg);

        //send to push task queue
        //amqpTemplate.convertAndSend("", "queue.push.task", msg);

        //send to delay queue
        amqpTemplate
                .convertAndSend("exchange.push.task.delay", "queue.push.task.delay", msg, new MessagePostProcessor() {
                    @Override
                    public org.springframework.amqp.core.Message postProcessMessage(
                            org.springframework.amqp.core.Message message) throws AmqpException {
                        message.getMessageProperties().setHeader("x-delay", 5000);//in millsecond
                        return message;

                    }
                });
    }
}
