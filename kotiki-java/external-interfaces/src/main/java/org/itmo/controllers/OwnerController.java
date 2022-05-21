package org.itmo.controllers;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.OwnerDto;
import org.itmo.services.AbstractOwnerService;
import org.itmo.services.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController extends BaseController {

    private final AbstractOwnerService ownerService;
    private final UserService userService;

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OwnerDto> getAll() {
        return ownerService.getAll();
    }

    @GetMapping("/get/{id}")
    public OwnerDto get(@PathVariable int id) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentOwnerId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return ownerService.get(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody OwnerDto owner) {
        ownerService.save(owner);
    }

    @PostMapping("/update")
    public void update(@RequestBody OwnerDto owner) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentOwnerId() != owner.getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (!ownerService.exists(owner.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ownerService.save(owner);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        if (userService.isCurrentUserNotAnAdmin()
                && userService.getCurrentOwnerId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        ownerService.delete(id);
    }
}
