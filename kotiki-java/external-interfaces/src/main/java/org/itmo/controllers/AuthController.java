package org.itmo.controllers;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserDto;
import org.itmo.services.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody UserDto user) {
        userService.save(user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@RequestBody UserDto user) {
        userService.save(user);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        userService.delete(id);
    }
}
