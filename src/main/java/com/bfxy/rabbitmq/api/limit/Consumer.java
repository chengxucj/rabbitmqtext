package com.bfxy.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        String exchange = "test_qos_exchange";
        String routingkey = "qos.#";
        String queueName = "test_qos_queue";

        //4.建立一个绑定关系
        //声明exchange
        channel.exchangeDeclare(exchange,"topic",true,false,null);
        //声明队列
        channel.queueDeclare(queueName,true,false,false,null);
        //进行队列和exchange的绑定
        channel.queueBind(queueName,exchange,routingkey);


        //1限流方式 第一件事情就是autoAck设置为false关闭自动签收机制

        //2在channel使用basicQos()方法
        channel.basicQos(0,1,false);
        channel.basicConsume(queueName,false,new MyConsumer(channel));

    }
}
