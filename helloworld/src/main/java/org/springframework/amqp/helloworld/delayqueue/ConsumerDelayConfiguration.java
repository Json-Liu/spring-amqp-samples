package org.springframework.amqp.helloworld.delayqueue;

import org.springframework.amqp.helloworld.async.annotation.HelloMessageListener;
import org.springframework.amqp.helloworld.sync.annotation.HelloWorldConfiguration;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerDelayConfiguration extends HelloWorldConfiguration {

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("queue.delay");
        container.setMessageListener(
                new MessageListenerAdapter(new HelloMessageListener(), new Jackson2JsonMessageConverter()));
        container.setConcurrentConsumers(1);
        return container;
    }

}
