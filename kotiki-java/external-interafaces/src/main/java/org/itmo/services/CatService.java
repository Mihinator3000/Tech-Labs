package org.itmo.services;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.CatDto;
import org.itmo.dto.rabbit.BreedDto;
import org.itmo.dto.rabbit.ColorDto;
import org.itmo.enums.Color;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.itmo.configs.CatsRabbitConfig.*;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class CatService implements AbstractCatService {

    private final RabbitTemplate template;

    public List<CatDto> getAll() {
        return (List<CatDto>)template.convertSendAndReceive(
                CATS_EXCHANGE,
                CATS_FULL_ACCESS_ROUTING_KEY,
                Optional.empty());
    }

    public CatDto get(int id) {
        return template.convertSendAndReceiveAsType(
                CATS_EXCHANGE,
                CATS_ACCESS_ROUTING_KEY,
                id,
                ParameterizedTypeReference.forType(CatDto.class));
    }

    public List<CatDto> getByBreed(String breed) {
        return (List<CatDto>)template.convertSendAndReceive(
                CATS_EXCHANGE,
                CATS_ACCESS_ROUTING_KEY,
                new BreedDto(breed, null));
    }

    public List<CatDto> getByColor(Color color) {
        return (List<CatDto>)template.convertSendAndReceive(
                CATS_EXCHANGE,
                CATS_ACCESS_ROUTING_KEY,
                new ColorDto(color, null));
    }

    public List<CatDto> getByOwnerId(int ownerId) {
        return (List<CatDto>)template.convertSendAndReceive(
                CATS_EXCHANGE,
                CATS_ACCESS_ROUTING_KEY,
                Optional.of(ownerId));
    }

    public List<CatDto> getByBreedAndOwnerId(String breed, int ownerId) {
        return (List<CatDto>)template.convertSendAndReceive(
                CATS_EXCHANGE,
                CATS_ACCESS_ROUTING_KEY,
                new BreedDto(breed, ownerId));
    }

    public List<CatDto> getByColorAndOwnerId(Color color, int ownerId) {
        return (List<CatDto>)template.convertSendAndReceive(
                CATS_EXCHANGE,
                CATS_ACCESS_ROUTING_KEY,
                new ColorDto(color, null));
    }
    public void save(CatDto cat) {
        template.convertAndSend(
                CATS_EXCHANGE,
                CATS_SAVE_ROUTING_KEY,
                cat);
    }

    public void delete(int id) {
        template.convertAndSend(
                CATS_EXCHANGE,
                CATS_DELETE_ROUTING_KEY,
                id);
    }

    public boolean exists(int id) {
        return Boolean.TRUE.equals(template.convertSendAndReceiveAsType(
                CATS_EXCHANGE,
                CATS_EXISTS_ROUTING_KEY,
                id,
                ParameterizedTypeReference.forType(boolean.class)));
    }
}
