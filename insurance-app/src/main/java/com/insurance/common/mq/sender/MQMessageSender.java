package com.insurance.common.mq.sender;

import com.insurance.common.config.JmsConfig;
import com.insurance.common.mq.message.MQMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class MQMessageSender {

    private final JmsTemplate jmsTemplate;

    public MQMessageSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    //    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        final MQMessage mqMessage = new MQMessage();
        mqMessage.setId(UUID.randomUUID());
        mqMessage.setMessage("MESSAGE");

        jmsTemplate.convertAndSend(JmsConfig.QUEUE, mqMessage);

        log.debug("Message has been sent to QUEUE: {0} with UUID: {1}", JmsConfig.QUEUE, mqMessage.getId());
    }
}
