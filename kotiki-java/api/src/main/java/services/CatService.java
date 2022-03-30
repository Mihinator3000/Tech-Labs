package services;

import dao.CatDao;
import models.Cat;

import java.util.List;

public class CatService {

    private final CatDao dao;

    public CatService() {
        dao = new CatDao();
    }

    public CatService(CatDao dao) {
        this.dao = dao;
    }

    public List<Cat> getAll() {
        return dao.getAll();
    }

    public Cat get(int id) {
        return dao.get(id);
    }

    public void add(Cat cat) {
        dao.add(cat);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public void update(Cat cat) {
        dao.update(cat);
    }
}
