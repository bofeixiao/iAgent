package com.idataai.common.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * @author iDataAI
 */
@Configuration
public class RabbitMQConfig {

    /**
     * AI任务队列
     */
    public static final String AI_TASK_QUEUE = "ai.task.queue";

    /**
     * AI任务交换机
     */
    public static final String AI_TASK_EXCHANGE = "ai.task.exchange";

    /**
     * AI任务路由键
     */
    public static final String AI_TASK_ROUTING_KEY = "ai.task.routing.key";

    /**
     * 支付通知队列
     */
    public static final String PAYMENT_NOTIFY_QUEUE = "payment.notify.queue";

    /**
     * 支付通知交换机
     */
    public static final String PAYMENT_NOTIFY_EXCHANGE = "payment.notify.exchange";

    /**
     * 支付通知路由键
     */
    public static final String PAYMENT_NOTIFY_ROUTING_KEY = "payment.notify.routing.key";

    /**
     * 消息转换器 - 使用JSON格式
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate配置
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /**
     * 监听器容器配置
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(10);
        return factory;
    }

    /**
     * AI任务队列
     */
    @Bean
    public Queue aiTaskQueue() {
        return QueueBuilder.durable(AI_TASK_QUEUE).build();
    }

    /**
     * AI任务交换机
     */
    @Bean
    public DirectExchange aiTaskExchange() {
        return ExchangeBuilder.directExchange(AI_TASK_EXCHANGE).durable(true).build();
    }

    /**
     * AI任务绑定
     */
    @Bean
    public Binding aiTaskBinding(Queue aiTaskQueue, DirectExchange aiTaskExchange) {
        return BindingBuilder.bind(aiTaskQueue).to(aiTaskExchange).with(AI_TASK_ROUTING_KEY);
    }

    /**
     * 支付通知队列
     */
    @Bean
    public Queue paymentNotifyQueue() {
        return QueueBuilder.durable(PAYMENT_NOTIFY_QUEUE).build();
    }

    /**
     * 支付通知交换机
     */
    @Bean
    public DirectExchange paymentNotifyExchange() {
        return ExchangeBuilder.directExchange(PAYMENT_NOTIFY_EXCHANGE).durable(true).build();
    }

    /**
     * 支付通知绑定
     */
    @Bean
    public Binding paymentNotifyBinding(Queue paymentNotifyQueue, DirectExchange paymentNotifyExchange) {
        return BindingBuilder.bind(paymentNotifyQueue).to(paymentNotifyExchange).with(PAYMENT_NOTIFY_ROUTING_KEY);
    }
}
