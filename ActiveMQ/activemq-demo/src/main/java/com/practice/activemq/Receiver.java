package com.practice.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("lbd",
                "lbd", "tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");

        MessageConsumer consumer = session.createConsumer(destination);

        while (true) {
            TextMessage msg = (TextMessage) consumer.receive();
            if (msg == null) {
                break;
            }
            System.out.println("msg message : " + msg.getText());
            msg.acknowledge();
        }
        connection.close();

    }
}
