package controllers;

import models.Owner;
import services.OwnerService;
import tools.ControllerException;

import java.util.List;

public class OwnerController {

    private final OwnerService service;

    public OwnerController() {
        service = new OwnerService();
    }

    public OwnerController(OwnerService service) {
        this.service = service;
    }

    public List<Owner> getAll() {
        try {
            return service.getAll();
        } catch (Exception e){
            throw new ControllerException(e.getMessage());
        }
    }

    public Owner get(int id) {
        try {
            return service.get(id);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void add(Owner owner) {
        try {
            service.add(owner);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            service.delete(id);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void update(Owner owner) {
        try {
            service.update(owner);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }
}
