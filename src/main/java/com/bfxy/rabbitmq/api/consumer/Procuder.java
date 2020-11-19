package com.bfxy.rabbitmq.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
*生产者
 * 自定义消费者
 */
public class Procuder {
    public static void main(String[] args) throws Exception {
        //1.创建connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.228.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.获取connection
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创建一个新的Channel
        Channel channel=connection.createChannel();

        String exchange = "test_consumer_exchange";
        String routingkey = "consumer.save";

        String msg = "Hello RabbitMQ consumer Message";

        //5.发送一条消息
        for (int i=0;i<5;i++) {
            channel.basicPublish(exchange, routingkey, true, null, msg.getBytes());
        }
    }
}
