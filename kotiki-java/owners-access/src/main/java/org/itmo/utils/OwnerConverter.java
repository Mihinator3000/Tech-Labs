package org.itmo.utils;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.OwnerDto;
import org.itmo.entities.Owner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerConverter {

    public OwnerDto toDto(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .build();
    }

    public Owner toModel(OwnerDto owner) {
        Owner.OwnerBuilder ownerBuilder = Owner.builder()
                .id(owner.getId())
                .name(owner.getName());

        return ownerBuilder.build();
    }
}
