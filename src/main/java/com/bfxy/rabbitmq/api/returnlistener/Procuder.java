package com.bfxy.rabbitmq.api.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
*生产者
 * return机制
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

        String exchange = "test_return_exchange";
        String routingkey = "return.save";
        String routingkeyError = "abc.save";

        String msg = "Hello RabbitMQ Return Message";

        //4.监控不可达消息
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("----handle return----");
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("properties:"+properties);
                System.out.println("body:"+new String(body));
            }
        });

        //5.发送一条消息
//        channel.basicPublish(exchange,routingkey,true,null,msg.getBytes());

        //6.发送一条错误的消息
        channel.basicPublish(exchange,routingkeyError,true,null,msg.getBytes());
    }
}
