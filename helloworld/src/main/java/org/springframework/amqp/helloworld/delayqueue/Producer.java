package org.springframework.amqp.helloworld.delayqueue;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

    public static void main(String[] args) throws Exception {
        new AnnotationConfigApplicationContext(ProducerDelayConfiguration.class);
    }

}
