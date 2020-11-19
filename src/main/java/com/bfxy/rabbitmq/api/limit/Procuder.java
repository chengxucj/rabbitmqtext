package com.bfxy.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
*生产者
 *消费限制
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

        String exchange = "test_qos_exchange";
        String routingkey = "qos.save";

        String msg = "Hello RabbitMQ qos Message";

        //5.发送一条消息
        for (int i=0;i<5;i++) {
            channel.basicPublish(exchange, routingkey, true, null, msg.getBytes());
        }
    }
}
