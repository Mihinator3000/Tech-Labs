package org.itmo.listeners;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.OwnerDto;
import org.itmo.services.AbstractOwnerService;
import org.itmo.utils.OwnerConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.itmo.configs.OwnersRabbitConfig.*;

@Component
@RequiredArgsConstructor
public class OwnerListener {

    private final AbstractOwnerService service;

    private final OwnerConverter converter;

    @RabbitListener(queues = OWNERS_FULL_ACCESS_QUEUE)
    public List<OwnerDto> getAll(String ignoredMessage) {
        return service.getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @RabbitListener(queues = OWNERS_CAT_ACCESS_QUEUE)
    public OwnerDto getByCatId(int catId) {
        return converter.toDto(service.getByCatId(catId));
    }

    @RabbitListener(queues = OWNERS_ACCESS_QUEUE)
    public OwnerDto get(int id) {
        return converter.toDto(service.get(id));
    }

    @RabbitListener(queues = OWNERS_SAVE_QUEUE)
    public void save(OwnerDto owner) {
        service.save(converter.toModel(owner));
    }

    @RabbitListener(queues = OWNERS_DELETE_QUEUE)
    public void delete(int id) {
        service.delete(id);
    }
}
