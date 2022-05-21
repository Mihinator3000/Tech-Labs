package org.itmo.listeners;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserDto;
import org.itmo.services.auth.UserService;
import org.itmo.utils.UserConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.itmo.configs.OwnersRabbitConfig.*;

@Component
@RequiredArgsConstructor
public class UserListener {

    private final UserService service;

    private final UserConverter converter;

    @RabbitListener(queues = USERS_GET_QUEUE)
    public UserDto get(String username) {
        return converter.toDto(service.get(username));
    }

    @RabbitListener(queues = USERS_SAVE_QUEUE)
    public void save(UserDto user) {
        service.save(converter.toModel(user));
    }

    @RabbitListener(queues = USERS_DELETE_QUEUE)
    public void delete(int id) {
        service.delete(id);
    }
}
