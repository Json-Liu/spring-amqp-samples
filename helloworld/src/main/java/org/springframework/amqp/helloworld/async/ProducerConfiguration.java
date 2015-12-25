package org.springframework.amqp.helloworld.async;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.helloworld.HelloWorldConfiguration;
import org.springframework.amqp.helloworld.Message;
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

        @Scheduled(fixedRate = 100)
        public void sendMessage() {

            int count = counter.incrementAndGet();
            Message msg = new Message("title " + count, "hello world " + count);

            rabbitTemplate.convertAndSend(msg);
        }
    }

}
