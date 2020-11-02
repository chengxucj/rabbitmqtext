package com.bfxy.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

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
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1.创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //通过链接工厂创建链接
         connectionFactory.newConnection();
    }
}
