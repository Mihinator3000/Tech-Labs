package services;

import dao.OwnerDao;
import models.Owner;

import java.util.List;

public class OwnerService {

    private final OwnerDao dao;

    public OwnerService() {
        dao = new OwnerDao();
    }

    public OwnerService(OwnerDao dao) {
        this.dao = dao;
    }

    public List<Owner> getAll() {
        return dao.getAll();
    }

    public Owner get(int id) {
        return dao.get(id);
    }

    public void add(Owner owner) {
        dao.add(owner);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public void update(Owner owner) {
        dao.update(owner);
    }
}
