package com.bfxy.rabbitmq.api.returnlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消费者
 * */
public class Consumer {
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

        String exchange = "test_return_exchange";
        String routingkey = "return.#";
        String queueName = "test_return_queue";

        //4.建立一个绑定关系
        channel.exchangeDeclare(exchange,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchange,routingkey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("消费者："+new String(delivery.getBody()));
        }

    }
}
