package com.bfxy.rabbitmq.api.consumer;

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

        String exchange = "test_consumer_exchange";
        String routingkey = "consumer.#";
        String queueName = "test_consumer_queue";

        //4.建立一个绑定关系
        //声明exchange
        channel.exchangeDeclare(exchange,"topic",true,false,null);
        //声明队列
        channel.queueDeclare(queueName,true,false,false,null);
        //进行队列和exchange的绑定
        channel.queueBind(queueName,exchange,routingkey);

        channel.basicConsume(queueName,true,new MyConsumer(channel));

    }
}
