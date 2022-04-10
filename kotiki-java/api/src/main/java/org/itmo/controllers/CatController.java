package org.itmo.controllers;

import org.itmo.dto.CatDto;
import org.itmo.enums.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.itmo.services.AbstractCatService;
import org.itmo.utils.CatConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cat")
public class CatController extends BaseController {

    private final AbstractCatService service;
    private final CatConverter converter;

    @Autowired
    public CatController(AbstractCatService service, CatConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/get/all")
    public List<CatDto> getAll() {
        return service
                .getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public CatDto get(@PathVariable int id) {
        return converter.toDto(service.get(id));
    }

    @GetMapping("/get/breed/{breed}")
    public List<CatDto> getByBreed(@PathVariable String breed) {
        return service
                .getByBreed(breed)
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/color/{color}")
    public List<CatDto> getByColor(@PathVariable String color) {
        return service
                .getByColor(Color.valueOf(color.toUpperCase()))
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public void create(@RequestBody CatDto cat) {
        service.save(converter.toModel(cat));
    }

    @PostMapping("/update")
    public void update(@RequestBody CatDto cat) {
        if (!service.exists(cat.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        service.save(converter.toModel(cat));
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
