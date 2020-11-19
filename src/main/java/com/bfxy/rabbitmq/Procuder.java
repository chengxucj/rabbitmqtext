package com.bfxy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 一些声明信息
 * ClassName: 刘玮易 <br/>
 * Description: <br/>
 * date: 2020/10/21 21:41<br/>
 *
 * @author 19684<br />
 * @since JDK 1.8
 *
 * 生产端代码
 */
public class Procuder {
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

        //4.通过channel发送数据
        for (int i=0;i<5;i++){
            String msg="hello rabbitMQ";
            //1 exchange交换机  2routingkey
            channel.basicPublish("","text001",null,msg.getBytes());
        }

        //5.记得要关闭相关的链接

        channel.close();
        connection.close();
    }
}
