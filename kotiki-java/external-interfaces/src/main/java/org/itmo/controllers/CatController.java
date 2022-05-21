package org.itmo.controllers;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.CatDto;
import org.itmo.enums.Color;
import org.itmo.services.AbstractOwnerService;
import org.itmo.services.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.itmo.services.AbstractCatService;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
public class CatController extends BaseController {

    private final AbstractCatService catService;
    private final AbstractOwnerService ownerService;
    private final UserService userService;

    @GetMapping("/get/all")
    public List<CatDto> getAll() {
        return userService
                .isCurrentUserNotAnAdmin()
                ? catService.getByOwnerId(userService.getCurrentOwnerId())
                : catService.getAll();
    }

    @GetMapping("/get/{id}")
    public CatDto get(@PathVariable int id) {
        CatDto cat = catService.get(id);
        if (userService.isCurrentUserNotAnAdmin()
                && ownerService.getByCatId(cat.getId()).getId() != userService.getCurrentOwnerId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return cat;
    }

    @GetMapping("/get/breed/{breed}")
    public List<CatDto> getByBreed(@PathVariable String breed) {
        return userService
                .isCurrentUserNotAnAdmin()
                ? catService.getByBreedAndOwnerId(breed, userService.getCurrentOwnerId())
                : catService.getByBreed(breed);
    }

    @GetMapping("/get/color/{color}")
    public List<CatDto> getByColor(@PathVariable String color) {
        var enumColor = Color.valueOf(color.toUpperCase());

        return userService
                .isCurrentUserNotAnAdmin()
                ? catService.getByColorAndOwnerId(enumColor, userService.getCurrentOwnerId())
                : catService.getByColor(enumColor);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody CatDto cat) {
        catService.save(cat);
    }

    @PostMapping("/update")
    public void update(@RequestBody CatDto cat) {
        if (userService.isCurrentUserNotAnAdmin()
                && ownerService.getByCatId(cat.getId()).getId() != userService.getCurrentOwnerId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (!catService.exists(cat.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        catService.save(cat);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        CatDto cat = catService.get(id);
        if (userService.isCurrentUserNotAnAdmin()
                && ownerService.getByCatId(cat.getId()).getId() != userService.getCurrentOwnerId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        catService.delete(id);
    }
}
