package org.itmo.controllers;

import org.itmo.dto.OwnerDto;
import org.itmo.services.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.itmo.services.AbstractOwnerService;
import org.itmo.utils.OwnerConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owner")
public class OwnerController extends BaseController {

    private final AbstractOwnerService ownerService;
    private final OwnerConverter converter;
    private final UserService userService;

    @Autowired
    public OwnerController(AbstractOwnerService ownerService, OwnerConverter converter, UserService userService) {
        this.ownerService = ownerService;
        this.converter = converter;
        this.userService = userService;
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OwnerDto> getAll() {
        return ownerService
                .getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public OwnerDto get(@PathVariable int id) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentOwnerId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return converter.toDto(ownerService.get(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody OwnerDto owner) {
        ownerService.save(converter.toModel(owner));
    }

    @PostMapping("/update")
    public void update(@RequestBody OwnerDto owner) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentOwnerId() != owner.getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (!ownerService.exists(owner.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ownerService.save(converter.toModel(owner));
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentOwnerId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        ownerService.delete(id);
    }
}
