package org.itmo.controllers;

import org.itmo.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.itmo.services.AbstractOwnerService;
import org.itmo.utils.OwnerConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owner")
public class OwnerController extends BaseController {

    private final AbstractOwnerService service;
    private final OwnerConverter converter;

    @Autowired
    public OwnerController(AbstractOwnerService service, OwnerConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/get/all")
    public List<OwnerDto> getAll() {
        return service
                .getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public OwnerDto get(@PathVariable int id) {
        return converter.toDto(service.get(id));
    }

    @PostMapping("/create")
    public void create(@RequestBody OwnerDto owner) {
        service.save(converter.toModel(owner));
    }

    @PostMapping("/update")
    public void update(@RequestBody OwnerDto owner) {
        if (!service.exists(owner.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        service.save(converter.toModel(owner));
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
