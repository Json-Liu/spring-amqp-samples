package org.springframework.amqp.helloworld.async;

import org.springframework.amqp.core.MessageListener;

public class HelloWorldHandler implements MessageListener {

    @Override
    public void onMessage(org.springframework.amqp.core.Message message) {

        System.out.println("Received: " + new String(message.getBody()));
    }

}
