/**
 * @(#)HelloWorldMessage.java, Dec 24, 2015. 
 * 
 * Created by Hoswey
 * 
 * Copyright 2015 Feigong, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.springframework.amqp.helloworld;

public class HelloWorldMessage {

    private String title;

    private String body;

    public HelloWorldMessage(String title, String body) {
        super();
        this.title = title;
        this.body = body;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

    @Override
    public String toString() {

        return "HelloWorldMessage [title=" + title + ", body=" + body + "]";
    }

}
