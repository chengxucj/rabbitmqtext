package com.bfxy.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
/**
 * confirmSelect消息投递模式
* 生产者
* */
public class Procuder {
    public static void main(String[] args) throws Exception {

        //1.创建connectionFactory
        ConnectionFactory   connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.228.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.获取connection
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创建一个新的Channel
        Channel channel=connection.createChannel();

        //4.指定我们的消息投递模式
        channel.confirmSelect();

        String exchangeName="test_confirm_exchange";
        String routingkey="confirm.save";

        //5.发送一条消息
        String msg="Hello rabbitMQ send confirm message!";
        channel.basicPublish(exchangeName,routingkey,null,msg.getBytes());

        //6.添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //失败的时候，deliveryTag：消息的唯一标签
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("------ack!--------");
            }

            //返回成功的时候
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("------no ack!--------");
            }
        });
    }
}
