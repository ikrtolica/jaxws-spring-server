package com.mulesoft.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMessageConsumer implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageConsumer.class);

    public void onMessage(Message message) {
        try {
            LOG.info("Received message: {}", ((TextMessage)message).getText());
            //System.out.println("Processed an order.");
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}