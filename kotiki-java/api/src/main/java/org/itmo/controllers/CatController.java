package org.itmo.controllers;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.CatDto;
import org.itmo.enums.Color;
import org.itmo.models.Cat;
import org.itmo.services.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.itmo.services.AbstractCatService;
import org.itmo.utils.CatConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
public class CatController extends BaseController {

    private final AbstractCatService catService;
    private final CatConverter converter;
    private final UserService userService;

    @GetMapping("/get/all")
    public List<CatDto> getAll() {
        List<Cat> cats = userService
                .isCurrentUserNotAnAdmin()
                ? catService.getByOwnerId(userService.getCurrentOwnerId())
                : catService.getAll();

        return cats
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public CatDto get(@PathVariable int id) {
        Cat cat = catService.get(id);
        if (userService.isCurrentUserNotAnAdmin()
                && cat.getOwner().getId() != userService.getCurrentOwnerId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return converter.toDto(cat);
    }

    @GetMapping("/get/breed/{breed}")
    public List<CatDto> getByBreed(@PathVariable String breed) {
        List<Cat> cats = userService
                .isCurrentUserNotAnAdmin()
                ? catService.getByBreedAndOwnerId(breed, userService.getCurrentOwnerId())
                : catService.getByBreed(breed);

        return cats
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/color/{color}")
    public List<CatDto> getByColor(@PathVariable String color) {
        var enumColor = Color.valueOf(color.toUpperCase());

        List<Cat> cats = userService
                .isCurrentUserNotAnAdmin()
                ? catService.getByColorAndOwnerId(enumColor, userService.getCurrentOwnerId())
                : catService.getByColor(enumColor);

        return cats
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody CatDto cat) {
        catService.save(converter.toModel(cat));
    }

    @PostMapping("/update")
    public void update(@RequestBody CatDto cat) {
        if (userService.isCurrentUserNotAnAdmin()
                && cat.getOwnerId() != userService.getCurrentOwnerId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (!catService.exists(cat.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        catService.save(converter.toModel(cat));
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        Cat cat = catService.get(id);
        if (userService.isCurrentUserNotAnAdmin()
                && cat.getOwner().getId() != userService.getCurrentOwnerId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        catService.delete(id);
    }
}
