package com.insurance.common.mq.receiver;

import com.insurance.common.config.JmsConfig;
import com.insurance.common.mq.message.MQMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class MQMessageReceiver {

    private static final Logger LOGGER = LogManager.getLogger(MQMessageReceiver.class);

    @JmsListener(destination = JmsConfig.QUEUE)
    public void receive(@Payload MQMessage mqMessage,
                        @Headers MessageHeaders headers,
                        Message message) {

        LOGGER.debug("Message has been received from QUEUE: {0} with UUID: {1} message: {2}", JmsConfig.QUEUE, mqMessage.getId(), mqMessage);
    }
}
