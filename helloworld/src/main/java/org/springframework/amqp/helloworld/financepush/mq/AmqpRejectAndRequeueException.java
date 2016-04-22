/**
 * @(#)AmqpRejectAndRequeueException.java, Jan 12, 2016.
 * <p>
 * Created by Hoswey
 * <p>
 * Copyright 2016 Feigong, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.springframework.amqp.helloworld.financepush.mq;

import org.springframework.amqp.AmqpException;

@SuppressWarnings("serial")
public class AmqpRejectAndRequeueException extends AmqpException {

    public AmqpRejectAndRequeueException(String message) {
        super(message);

    }

}

