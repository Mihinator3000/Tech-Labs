package org.itmo.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class CatsRabbitConfig {

    public static final String CATS_ACCESS_QUEUE = "cats-access-queue";
    public static final String CATS_FULL_ACCESS_QUEUE = "cats-full-access-queue";
    public static final String CATS_SAVE_QUEUE = "cats-save-queue";
    public static final String CATS_DELETE_QUEUE = "cats-delete-queue";
    public static final String CATS_EXIST_QUEUE = "cats-exist-queue";

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
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
