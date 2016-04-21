package org.springframework.amqp.helloworld.delayqueue;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.helloworld.sync.annotation.HelloWorldConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ProducerDelayConfiguration extends HelloWorldConfiguration {

    @Bean
    public ScheduledProducer scheduledProducer() {

        return new ScheduledProducer();
    }

    @Bean
    public BeanPostProcessor postProcessor() {

        return new ScheduledAnnotationBeanPostProcessor();
    }

    static class ScheduledProducer {

        @Autowired
        private volatile RabbitTemplate rabbitTemplate;

        private final AtomicInteger counter = new AtomicInteger();

        @Scheduled(fixedRate = 10000)
        public void sendMessage() {

            int count = counter.incrementAndGet();

            String title = "title " + count;
            String body = "hello world " + count;
            Map<String, Object> msg = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

            msg.put("title", title);
            msg.put("body", body);
            msg.put("sendTime", System.currentTimeMillis());

            rabbitTemplate.convertAndSend("sys.exchange.topic.delay", "queue.delay", msg, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("x-delay", 5000);
                    return message;

                }
            });

        }
    }
}
