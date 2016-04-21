package org.springframework.amqp.helloworld.sync.annotation;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class HelloWorldConfiguration {

    protected final String queueName = "queue.hello.world";

    protected final String routingKey = "route.hello.world";

    protected final String exchange = "amq.topic";

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("119.147.176.188", 5672);
        connectionFactory.setVirtualHost("vh.hello_world");
        connectionFactory.setUsername("helloworld");
        connectionFactory.setPassword("helloworld");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {

        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {

        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //Default setting - The routing key is set to the name of the queue by the broker for the default exchange.
        template.setRoutingKey(routingKey);

        //Default setting - exhcnage
        template.setExchange(exchange);

        //Default setting - Message Converter
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        //Where we will synchronously receive messages from
        template.setQueue(this.queueName);

        ExponentialBackOffPolicy bop = new ExponentialBackOffPolicy();
        bop.setInitialInterval(100);
        bop.setMultiplier(1);
        bop.setMaxInterval(30000);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        //how many attempts
        simpleRetryPolicy.setMaxAttempts(3);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(bop);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        template.setRetryTemplate(retryTemplate);

        return template;
    }

    @Bean
    // Every queue is bound to the default direct exchange
    public Queue helloWorldQueue() {

        Queue queue = new Queue(this.queueName);
        queue.setShouldDeclare(false);
        return queue;
    }

    /*
    @Bean 
    public Binding binding() {
    	return declare(new Binding(helloWorldQueue(), defaultDirectExchange()));
    }*/

    /*	
    @Bean
    public TopicExchange helloExchange() {
    	return declare(new TopicExchange("hello.world.exchange"));
    }*/

    /*
    public Queue declareUniqueQueue(String namePrefix) {
    	Queue queue = new Queue(namePrefix + "-" + UUID.randomUUID());
    	rabbitAdminTemplate().declareQueue(queue);
    	return queue;
    }
    
    // if the default exchange isn't configured to your liking....
    @Bean Binding declareP2PBinding(Queue queue, DirectExchange exchange) {
    	return declare(new Binding(queue, exchange, queue.getName()));
    }
    
    @Bean Binding declarePubSubBinding(String queuePrefix, FanoutExchange exchange) {
    	return declare(new Binding(declareUniqueQueue(queuePrefix), exchange));
    }
    
    @Bean Binding declarePubSubBinding(UniqueQueue uniqueQueue, TopicExchange exchange) {
    	return declare(new Binding(uniqueQueue, exchange));
    }
    
    @Bean Binding declarePubSubBinding(String queuePrefix, TopicExchange exchange, String routingKey) {
    	return declare(new Binding(declareUniqueQueue(queuePrefix), exchange, routingKey));
    }*/

}
