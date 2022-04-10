package org.itmo.utils;

import org.itmo.dto.CatDto;
import org.itmo.models.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.itmo.services.CatService;
import org.itmo.services.OwnerService;

import java.util.stream.Collectors;

@Component
public class CatConverter {

    private final CatService catService;
    private final OwnerService ownerService;

    @Autowired
    public CatConverter(CatService catService, OwnerService ownerService) {
        this.catService = catService;
        this.ownerService = ownerService;
    }

    public CatDto toDto(Cat cat) {
        return CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthDate(cat.getBirthDate())
                .breed(cat.getBreed())
                .color(cat.getColor())
                .ownerId(cat.getOwner() == null ? null : cat.getOwner().getId())
                .friendIds(cat.getFriends()
                        .stream()
                        .map(Cat::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Cat toModel(CatDto cat) {
        Cat.CatBuilder catBuilder = Cat.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthDate(cat.getBirthDate())
                .breed(cat.getBreed())
                .color(cat.getColor());

        if (cat.getOwnerId() != null)
            catBuilder.owner(ownerService.get(cat.getOwnerId()));

        if (cat.getFriendIds() != null)
            catBuilder.friends(cat.getFriendIds()
                    .stream()
                    .map(catService::get)
                    .collect(Collectors.toList()));

        return catBuilder.build();
    }
}
