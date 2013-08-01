package com.mulesoft.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.mulesoft.jms.SimpleMessageProducer;
import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService()
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class UserOperationService {
    private static final Logger LOG = LoggerFactory.getLogger(UserOperationService.class);
    private static final String HELLO = "Your order is being processed,";

    @WebMethod(operationName = "sayHello")
    public String sayHelloToTheUser(@WebParam(name = "name") String userName) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("applicationContextJMSProducer.xml");
        SimpleMessageProducer producer = (SimpleMessageProducer) context.getBean("messageProducer");

        try {
            producer.sendMessage(userName);
        } catch (JMSException e) {
            LOG.error("Could not send a message.");
        }

        LOG.info("Returning a confirmation message.");

        return HELLO + " " + userName;
    }
}