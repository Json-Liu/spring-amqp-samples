package org.springframework.amqp.helloworld.async.annotation;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.helloworld.sync.annotation.HelloWorldConfiguration;
import org.springframework.amqp.helloworld.sync.annotation.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@Configuration
public class ProducerConfiguration extends HelloWorldConfiguration {

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

        @Scheduled(fixedRate = 100000)
        public void sendMessage() {

            int count = counter.incrementAndGet();

            String title = "title " + count;
            String body = "hello world " + count;
            Message msg = new Message(title, body);
            System.out.println("sendMessage T1 Body : " + body);
            rabbitTemplate.convertAndSend(msg);
            System.out.println("sendMessage T2 Body : " + body);

        }
    }

}
