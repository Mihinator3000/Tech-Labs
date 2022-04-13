package org.itmo.controllers;

import org.itmo.dto.UserDto;
import org.itmo.services.auth.UserService;
import org.itmo.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public AuthController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody UserDto user) {
        userService.save(userConverter.toModel(user));
    }

    @PostMapping("/update")
    public void update(@RequestBody UserDto user) {
        if (userService.isCurrentUserNotAnAdmin()
                && !userService.getCurrentUsername().equals(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        userService.save(userConverter.toModel(user));
    }
}
