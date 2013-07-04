package com.mulesoft.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import java.util.HashMap;
import java.util.Map;

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
    private static final String HELLO = "Hello";

    @WebMethod(operationName = "sayHello")
    public String sayHelloToTheUser(@WebParam(name = "name") String userName) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("./MessageContext.xml");
        SimpleMessageProducer producer = (SimpleMessageProducer) context.getBean("messageProducer");

        try {
            producer.sendMessages(userName);
        } catch (JMSException e) {
            System.out.println("Could not send a message.");
        }

        LOG.info("Returning a HELLO message.");

        return HELLO + " " + userName;
    }
}