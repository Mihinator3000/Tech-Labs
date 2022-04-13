package org.itmo.services;

import org.itmo.dao.CatDao;
import org.itmo.enums.Color;
import org.itmo.models.Cat;
import org.itmo.tools.DalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService implements AbstractCatService {

    private final CatDao dao;

    @Autowired
    public CatService(CatDao dao) {
        this.dao = dao;
    }

    public List<Cat> getAll() {
        return dao.findAll();
    }

    public Cat get(int id) {
        Optional<Cat> result = dao.findById(id);
        if (result.isEmpty())
            throw new DalException("No cat with such id found");

        return result.get();
    }

    public List<Cat> getByBreed(String breed) {
        return dao.findByBreed(breed);
    }

    public List<Cat> getByColor(Color color) {
        return dao.findByColor(color);
    }

    public List<Cat> getByOwnerId(int ownerId) {
        return dao.findByOwnerId(ownerId);
    }

    public List<Cat> getByBreedAndOwnerId(String breed, int ownerId) {
        return dao.findByBreedAndOwnerId(breed, ownerId);
    }

    public List<Cat> getByColorAndOwnerId(Color color, int ownerId) {
        return dao.findByColorAndOwnerId(color, ownerId);
    }

    public void save(Cat cat) {
        dao.save(cat);
    }

    public void delete(int id) {
        dao.deleteById(id);
    }

    public boolean exists(int id) {
        return dao.existsById(id);
    }
}
