package controllers;

import dto.CatDto;
import enums.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.AbstractCatService;
import utils.CatConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cat")
public class CatController {

    private final AbstractCatService service;
    private final CatConverter converter;

    @Autowired
    public CatController(AbstractCatService service, CatConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/get/all")
    public List<CatDto> getAll() {
        try {
            return service
                    .getAll()
                    .stream()
                    .map(converter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public CatDto get(@PathVariable int id) {
        try {
            return converter.toDto(service.get(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/breed/{breed}")
    public List<CatDto> getByBreed(@PathVariable String breed) {
        try {
            return service
                    .getByBreed(breed)
                    .stream()
                    .map(converter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/color/{color}")
    public List<CatDto> getByColor(@PathVariable String color) {
        try {
            return service
                    .getByColor(Color.valueOf(color.toUpperCase()))
                    .stream()
                    .map(converter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody CatDto cat) {
        try {
            service.save(converter.toModel(cat));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public void update(@RequestBody CatDto cat) {
        if (!service.exists(cat.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        try {
            service.save(converter.toModel(cat));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
