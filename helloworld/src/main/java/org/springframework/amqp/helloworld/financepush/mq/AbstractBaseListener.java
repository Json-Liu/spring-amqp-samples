package org.springframework.amqp.helloworld.financepush.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by hongshuwei on 2/14/16.
 */
public abstract class AbstractBaseListener<T> implements MessageListener {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> msgClass;

    @SuppressWarnings("unchecked")
    public AbstractBaseListener() {

        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        msgClass = (Class<T>) types[0];
    }

    @Override
    public void onMessage(Message message) {
        String payload = null;
        try {
            payload = new String(message.getBody(), "utf-8");
            logger.debug("[cmd=onMessage,payload={}]", payload);
            T msgObj = objectMapper.readValue(payload, msgClass);
            onMessageInternal(msgObj);
        } catch (AmqpRejectAndRequeueException e) {
            throw e;
        } catch (RuntimeException | IOException e) {
            logger.error("", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    protected abstract void onMessageInternal(T message);
}
