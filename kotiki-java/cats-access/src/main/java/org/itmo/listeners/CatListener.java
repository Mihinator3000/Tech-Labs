package org.itmo.listeners;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.CatDto;
import org.itmo.dto.rabbit.BreedDto;
import org.itmo.dto.rabbit.ColorDto;
import org.itmo.dto.rabbit.OwnerIdDto;
import org.itmo.services.AbstractCatService;
import org.itmo.utils.CatConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.itmo.configs.CatsRabbitConfig.*;

@Component
@RequiredArgsConstructor
public class CatListener {

    private final AbstractCatService service;

    private final CatConverter converter;

    @RabbitListener(queues = CATS_FULL_ACCESS_QUEUE)
    public List<CatDto> getAll(OwnerIdDto ownerId) {
        return (ownerId.getId() == null
                ? service.getAll()
                : service.getByOwnerId(ownerId.getId()))
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @RabbitListener(queues = CATS_ACCESS_QUEUE)
    public CatDto get(int id) {
        return converter.toDto(service.get(id));
    }

    @RabbitListener(queues = CATS_ACCESS_QUEUE)
    public List<CatDto> getByBreed(BreedDto breedDto) {
        return (breedDto.getOwnerId() == null
                ? service.getByBreed(breedDto.getBreed())
                : service.getByBreedAndOwnerId(breedDto.getBreed(), breedDto.getOwnerId()))
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @RabbitListener(queues = CATS_ACCESS_QUEUE)
    public List<CatDto> getByColor(ColorDto colorDto) {
        return (colorDto.getOwnerId() == null
                ? service.getByColor(colorDto.getColor())
                : service.getByColorAndOwnerId(colorDto.getColor(), colorDto.getOwnerId()))
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @RabbitListener(queues = CATS_SAVE_QUEUE)
    public void save(CatDto cat) {
        service.save(converter.toModel(cat));
    }

    @RabbitListener(queues = CATS_DELETE_QUEUE)
    public void delete(int id) {
        service.delete(id);
    }

    @RabbitListener(queues = CATS_EXIST_QUEUE)
    public boolean exists(int id) {
        return service.exists(id);
    }
}
