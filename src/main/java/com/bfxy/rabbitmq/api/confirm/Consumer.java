package com.bfxy.rabbitmq.api.confirm;

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

        String exchangeName="test_confirm_exchange";
        String routingkey="confirm.#";
        String queueName="test_confirm_queue";

        //4.声明交换机和队列然后进行绑定设置，最后指定路由key
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingkey);

        //5.创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,queueingConsumer);
        while (true){
           QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
           String msg=new String(delivery.getBody());

            System.out.println("消费端"+msg);
        }
    }
}
