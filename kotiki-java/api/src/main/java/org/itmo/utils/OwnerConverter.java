package org.itmo.utils;

import org.itmo.dto.OwnerDto;
import org.itmo.models.Cat;
import org.itmo.models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.itmo.services.AbstractCatService;

import java.util.stream.Collectors;

@Component
public class OwnerConverter {

    private final AbstractCatService catService;

    @Autowired
    public OwnerConverter(AbstractCatService catService) {
        this.catService = catService;
    }

    public OwnerDto toDto(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .catIds(owner.getCats()
                        .stream()
                        .map(Cat::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Owner toModel(OwnerDto owner) {
        Owner.OwnerBuilder ownerBuilder = Owner.builder()
                .id(owner.getId())
                .name(owner.getName());

        if (owner.getCatIds() != null)
            ownerBuilder.cats(owner.getCatIds()
                    .stream()
                    .map(catService::get)
                    .collect(Collectors.toList()));

        return ownerBuilder.build();
    }
}
