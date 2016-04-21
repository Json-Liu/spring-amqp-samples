package org.springframework.amqp.helloworld.async.annotation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HelloMessageListener implements MessageListener {

    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onMessage(org.springframework.amqp.core.Message message) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        try {
            String payLoad = new String(message.getBody(), "utf-8");
            Map<String, Object> data = objectMapper.readValue(payLoad, new TypeReference<HashMap<String, Object>>() {
            });

            Long sendTime = (Long) data.get("sendTime");
            if (sendTime == null)
                return;
            long using = System.currentTimeMillis() - sendTime;
            System.out.println("Now: " + sdf.format(new Date()) + ",Receive: " + payLoad + ",using=" + using);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
