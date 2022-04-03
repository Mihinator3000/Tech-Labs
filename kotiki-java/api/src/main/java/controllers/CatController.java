package controllers;

import models.Cat;
import services.CatService;
import tools.ControllerException;

import java.util.List;

public class CatController {

    private final CatService service;

    public CatController() {
        service = new CatService();
    }

    public CatController(CatService service) {
        this.service = service;
    }

    public List<Cat> getAll() {
        try {
            return service.getAll();
        } catch (Exception e){
            throw new ControllerException(e.getMessage());
        }
    }

    public Cat get(int id) {
        try {
            return service.get(id);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void add(Cat cat) {
        try {
            service.add(cat);
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

    public void update(Cat cat) {
        try {
            service.update(cat);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }
}
