package org.itmo.utils;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.CatDto;
import org.itmo.entities.Cat;
import org.itmo.services.CatService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CatConverter {

    private final CatService catService;

    public CatDto toDto(Cat cat) {
        return CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthDate(cat.getBirthDate())
                .breed(cat.getBreed())
                .color(cat.getColor())
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

        /*Cat dbCat = catService.get(cat.getId());
        if (dbCat != null)
            catBuilder.owner(dbCat.getOwner());*/

        if (cat.getFriendIds() != null)
            catBuilder.friends(cat.getFriendIds()
                    .stream()
                    .map(catService::get)
                    .collect(Collectors.toList()));

        return catBuilder.build();
    }
}
