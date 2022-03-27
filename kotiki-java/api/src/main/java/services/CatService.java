package services;

import dao.CatDao;
import models.Cat;

import java.util.List;

public class CatService {

    private final CatDao dao = new CatDao();

    public List<Cat> getAll() {
        return dao.getAll();
    }

    public Cat get(int id) {
        return dao.getById(id);
    }

    public void add(Cat cat) {
        dao.add(cat);
    }

    public void delete(Cat cat) {
        dao.delete(cat);
    }

    public void update(Cat cat) {
        dao.update(cat);
    }
}
