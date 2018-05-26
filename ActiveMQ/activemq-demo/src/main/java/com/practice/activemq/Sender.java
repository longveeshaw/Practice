package com.practice.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("lbd",
                "lbd",
                "tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 5; i++) {
            TextMessage msg = session.createTextMessage("hello" + i);
            producer.send(msg, DeliveryMode.NON_PERSISTENT, 0, -1);
            System.out.println("send message : "+msg.getText());
        }
        session.commit();
        connection.close();


    }
}
