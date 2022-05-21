package org.itmo.services;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.OwnerDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.itmo.configs.OwnersRabbitConfig.*;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class OwnerService implements AbstractOwnerService {

    private final RabbitTemplate template;

    public List<OwnerDto> getAll() {
        return (List<OwnerDto>)template.convertSendAndReceive(
                OWNERS_EXCHANGE,
                OWNERS_FULL_ACCESS_ROUTING_KEY,
                "message");
    }

    public OwnerDto get(int id) {
        return template.convertSendAndReceiveAsType(
                OWNERS_EXCHANGE,
                OWNERS_ACCESS_ROUTING_KEY,
                id,
                ParameterizedTypeReference.forType(OwnerDto.class));
    }

    public OwnerDto getByCatId(int catId) {
        return template.convertSendAndReceiveAsType(
                OWNERS_EXCHANGE,
                OWNERS_CAT_ACCESS_ROUTING_KEY,
                catId,
                ParameterizedTypeReference.forType(OwnerDto.class));
    }

    public void save(OwnerDto owner) {
        template.convertAndSend(
                OWNERS_EXCHANGE,
                OWNERS_SAVE_ROUTING_KEY,
                owner);
    }

    public void delete(int id) {
        template.convertAndSend(
                OWNERS_EXCHANGE,
                OWNERS_DELETE_ROUTING_KEY,
                id);
    }

    public boolean exists(int id) {
        return Boolean.TRUE.equals(template.convertSendAndReceiveAsType(
                OWNERS_EXCHANGE,
                OWNERS_DELETE_ROUTING_KEY,
                id,
                ParameterizedTypeReference.forType(boolean.class)));
    }
}
