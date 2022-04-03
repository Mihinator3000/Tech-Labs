package controllers;

import dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.AbstractOwnerService;
import utils.OwnerConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final AbstractOwnerService service;
    private final OwnerConverter converter;

    @Autowired
    public OwnerController(AbstractOwnerService service, OwnerConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/get/all")
    public List<OwnerDto> getAll() {
        try {
            return service
                    .getAll()
                    .stream()
                    .map(converter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public OwnerDto get(@PathVariable int id) {
        try {
            return converter.toDto(service.get(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public void create(@RequestBody OwnerDto owner) {
        try {
            service.save(converter.toModel(owner));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public void update(@RequestBody OwnerDto owner) {
        if (!service.exists(owner.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        try {
            service.save(converter.toModel(owner));
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
