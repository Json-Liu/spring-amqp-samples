package org.springframework.amqp.helloworld.async.annotation;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.MessageListener;

public class HelloMessageListener implements MessageListener {

    @Override
    public void onMessage(org.springframework.amqp.core.Message message) {

        try {
            System.out.println("Receive: " + new String(message.getBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
