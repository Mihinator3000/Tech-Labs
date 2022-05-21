package org.itmo.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class OwnersRabbitConfig {

    public static final String OWNERS_EXCHANGE = "owners-exchange";

    public static final String OWNERS_ACCESS_QUEUE = "owners-access-queue";
    public static final String OWNERS_FULL_ACCESS_QUEUE = "owners-full-access-queue";
    public static final String OWNERS_CAT_ACCESS_QUEUE = "owners-cat-access-queue";
    public static final String OWNERS_SAVE_QUEUE = "owners-save-queue";
    public static final String OWNERS_DELETE_QUEUE = "owners-delete-queue";
    public static final String OWNERS_EXIST_QUEUE = "owners-exist-queue";

    public static final String USERS_GET_QUEUE = "users-get-queue";
    public static final String USERS_SAVE_QUEUE = "users-save-queue";
    public static final String USERS_DELETE_QUEUE = "users-delete-queue";

    public static final String OWNERS_ACCESS_ROUTING_KEY = "owners-access-routing-key";
    public static final String OWNERS_FULL_ACCESS_ROUTING_KEY = "owners-full-access-routing-key";
    public static final String OWNERS_CAT_ACCESS_ROUTING_KEY = "owners-cat-routing-key";
    public static final String OWNERS_SAVE_ROUTING_KEY = "owners-save-routing-key";
    public static final String OWNERS_DELETE_ROUTING_KEY = "owners-delete-routing-key";
    public static final String OWNERS_EXIST_ROUTING_KEY = "owners-exist-routing-key";

    public static final String USERS_GET_ROUTING_KEY = "users-get-routing-key";
    public static final String USERS_SAVE_ROUTING_KEY = "users-save-routing-key";
    public static final String USERS_DELETE_ROUTING_KEY = "users-delete-routing-key";

    @Bean
    public TopicExchange ownersExchange() {
        return new TopicExchange(OWNERS_EXCHANGE);
    }

    @Bean
    public Queue ownersAccessQueue() {
        return new Queue(OWNERS_ACCESS_QUEUE);
    }

    @Bean
    public Queue ownersFullAccessQueue() {
        return new Queue(OWNERS_FULL_ACCESS_QUEUE);
    }

    @Bean
    public Queue ownersCatAccessQueue() {
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
    public Binding ownersAccessBinding() {
        return BindingBuilder.bind(ownersAccessQueue()).to(ownersExchange()).with(OWNERS_ACCESS_ROUTING_KEY);
    }

    @Bean
    public Binding ownersFullAccessBinding() {
        return BindingBuilder.bind(ownersFullAccessQueue()).to(ownersExchange()).with(OWNERS_FULL_ACCESS_ROUTING_KEY);
    }

    @Bean
    public Binding ownersCatAccessBinding() {
        return BindingBuilder.bind(ownersCatAccessQueue()).to(ownersExchange()).with(OWNERS_CAT_ACCESS_ROUTING_KEY);
    }

    @Bean
    public Binding ownersSaveBinding() {
        return BindingBuilder.bind(ownersSaveQueue()).to(ownersExchange()).with(OWNERS_SAVE_ROUTING_KEY);
    }

    @Bean
    public Binding ownersDeleteBinding() {
        return BindingBuilder.bind(ownersDeleteQueue()).to(ownersExchange()).with(OWNERS_DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding ownersExistBinding() {
        return BindingBuilder.bind(ownersExistQueue()).to(ownersExchange()).with(OWNERS_EXIST_ROUTING_KEY);
    }

    @Bean
    public Binding usersGetBinding() {
        return BindingBuilder.bind(usersGetQueue()).to(ownersExchange()).with(USERS_GET_ROUTING_KEY);
    }

    @Bean
    public Binding usersSaveBinding() {
        return BindingBuilder.bind(usersSaveQueue()).to(ownersExchange()).with(USERS_SAVE_ROUTING_KEY);
    }

    @Bean
    public Binding usersDeleteBinding() {
        return BindingBuilder.bind(usersDeleteQueue()).to(ownersExchange()).with(USERS_DELETE_ROUTING_KEY);
    }
}
