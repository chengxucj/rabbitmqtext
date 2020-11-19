package com.bfxy.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一些声明信息
 * ClassName: 刘玮易 <br/>
 * Description: <br/>
 * date: 2020/10/21 21:42<br/>
 *
 * @author 19684<br />
 * @since JDK 1.8
 *
 *消费端代码
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1.创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.228.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.通过链接工厂创建链接
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创建一个Channel
        Channel channel = connection.createChannel();

        //4.生命（创建）一个队列        b：数据持久化 b1：独占，按顺序  b2：脱离了EXchanges自动删除
        String queueName = "text001";
        channel.queueDeclare(queueName,true,false,false,null);

        //5.创建一个消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6.设置channel
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            //7.获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg=delivery.getBody().toString();
            System.out.println("消费者："+msg);
            //Envelope envelope = delivery.getEnvelope();

        }
    }
}
