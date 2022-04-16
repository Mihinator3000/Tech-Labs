package org.itmo.utils;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserDto;
import org.itmo.models.auth.User;
import org.itmo.services.AbstractOwnerService;
import org.itmo.services.auth.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final AbstractOwnerService ownerService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User toModel(UserDto user) {
        User.UserBuilder userBuilder = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()));

        if (user.getOwnerId() != null)
            userBuilder.owner(ownerService.get(user.getOwnerId()));

        if (user.getRoles() != null)
            userBuilder.roles(user.getRoles()
                .stream()
                .map(roleService::getByName)
                .collect(Collectors.toList()));

        return userBuilder.build();
    }
}
