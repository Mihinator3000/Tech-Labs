package org.itmo.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class OwnersRabbitConfig {

    public static final String OWNERS_FULL_ACCESS_QUEUE = "owners-full-access-queue";
    public static final String OWNERS_ACCESS_QUEUE = "owners-access-queue";
    public static final String OWNERS_CAT_ACCESS_QUEUE = "owners-cat-access-queue";
    public static final String OWNERS_SAVE_QUEUE = "owners-save-queue";
    public static final String OWNERS_DELETE_QUEUE = "owners-delete-queue";
    public static final String OWNERS_EXIST_QUEUE = "owners-exist-queue";

    public static final String USERS_GET_QUEUE = "users-get-queue";
    public static final String USERS_SAVE_QUEUE = "users-save-queue";
    public static final String USERS_DELETE_QUEUE = "users-delete-queue";

    @Bean
    public Queue ownersAccessQueue() {
        return new Queue(OWNERS_ACCESS_QUEUE);
    }

    @Bean
    public Queue ownersFullAccessQueue() {
        return new Queue(OWNERS_FULL_ACCESS_QUEUE);
    }


    @Bean Queue ownersCatAccessQueue() {
        return new Queue(OWNERS_CAT_ACCESS_QUEUE);
    }

    @Bean
    public Queue ownersSaveQueue() {
        return new Queue(OWNERS_SAVE_QUEUE);
    }

    @Bean
    public Queue ownersDeleteQueue() {
        return new Queue(OWNERS_DELETE_QUEUE);
    }

    @Bean
    public Queue ownersExistQueue() {
        return new Queue(OWNERS_EXIST_QUEUE);
    }

    @Bean
    public Queue usersGetQueue() {
        return new Queue(USERS_GET_QUEUE);
    }

    @Bean
    public Queue usersSaveQueue() {
        return new Queue(USERS_SAVE_QUEUE);
    }

    @Bean
    public Queue usersDeleteQueue() {
        return new Queue(USERS_DELETE_QUEUE);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
