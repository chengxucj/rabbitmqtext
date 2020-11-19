package com.bfxy.rabbitmq.api.consumer;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.err.println("----consumer message----");
        System.err.println("consumerTag:"+consumerTag);
        System.err.println("envelope:"+envelope);
        System.err.println("properties:"+properties);
        System.err.println("body:"+new String(body));

    }
}
