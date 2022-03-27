package services;

import dao.OwnerDao;
import models.Owner;

import java.util.List;

public class OwnerService {

    private OwnerDao dao;

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
        return dao.getById(id);
    }

    public void add(Owner owner) {
        dao.add(owner);
    }

    public void delete(int owner) {
        dao.delete(owner);
    }

    public void update(Owner owner) {
        dao.update(owner);
    }
}
