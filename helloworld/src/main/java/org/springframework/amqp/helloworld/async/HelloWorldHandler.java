package org.springframework.amqp.helloworld.async;

import org.springframework.amqp.helloworld.Message;

public class HelloWorldHandler {

    public void handleMessage(Message message) {

        System.out.println("Received: " + message);
    }

}
