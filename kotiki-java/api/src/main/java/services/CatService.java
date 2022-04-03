package services;

import dao.CatDao;
import enums.Color;
import models.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tools.DalException;

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
