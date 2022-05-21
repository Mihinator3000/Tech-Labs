package org.itmo.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class CatsRabbitConfig {

    public static final String CATS_EXCHANGE = "cats-exchange";

    public static final String CATS_ACCESS_QUEUE = "cats-access-queue";
    public static final String CATS_FULL_ACCESS_QUEUE = "cats-full-access-queue";
    public static final String CATS_SAVE_QUEUE = "cats-save-queue";
    public static final String CATS_DELETE_QUEUE = "cats-delete-queue";
    public static final String CATS_EXIST_QUEUE = "cats-exist-queue";

    public static final String CATS_ACCESS_ROUTING_KEY = "cats-access-routing-key";
    public static final String CATS_FULL_ACCESS_ROUTING_KEY = "cats-full-access-routing-key";
    public static final String CATS_SAVE_ROUTING_KEY = "cats-save-routing-key";
    public static final String CATS_DELETE_ROUTING_KEY = "cats-delete-routing-key";
    public static final String CATS_EXISTS_ROUTING_KEY = "cats-exist-routing-key";

    @Bean
    public TopicExchange catsExchange() {
        return new TopicExchange(CATS_EXCHANGE);
    }

    @Bean
    public Queue catsAccessQueue() {
        return new Queue(CATS_ACCESS_QUEUE);
    }

    @Bean
    public Queue catsFullAccessQueue() {
        return new Queue(CATS_FULL_ACCESS_QUEUE);
    }

    @Bean
    public Queue catsSaveQueue() {
        return new Queue(CATS_SAVE_QUEUE);
    }

    @Bean
    public Queue catsDeleteQueue() {
        return new Queue(CATS_DELETE_QUEUE);
    }

    @Bean
    public Queue catsExistQueue() {
        return new Queue(CATS_EXIST_QUEUE);
    }

    @Bean
    public Binding catsAccessBinding() {
        return BindingBuilder.bind(catsAccessQueue()).to(catsExchange()).with(CATS_ACCESS_ROUTING_KEY);
    }

    @Bean
    public Binding catsFullAccessBinding() {
        return BindingBuilder.bind(catsFullAccessQueue()).to(catsExchange()).with(CATS_FULL_ACCESS_ROUTING_KEY);
    }

    @Bean
    public Binding catsSaveBinding() {
        return BindingBuilder.bind(catsSaveQueue()).to(catsExchange()).with(CATS_SAVE_ROUTING_KEY);
    }

    @Bean
    public Binding catsDeleteBinding() {
        return BindingBuilder.bind(catsDeleteQueue()).to(catsExchange()).with(CATS_DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding catsExistBinding() {
        return BindingBuilder.bind(catsExistQueue()).to(catsExchange()).with(CATS_EXISTS_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
