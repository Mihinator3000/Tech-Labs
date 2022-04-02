package services;

import dao.OwnerDao;
import models.Cat;
import models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.DalException;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService implements AbstractOwnerService {

    private final OwnerDao dao;

    @Autowired
    public OwnerService(OwnerDao dao) {
        this.dao = dao;
    }

    public List<Owner> getAll() {
        return dao.findAll();
    }

    public Owner get(int id) {
        Optional<Owner> result = dao.findById(id);
        if (result.isEmpty())
            throw new DalException("No owner with such id found");

        return result.get();
    }

    public void save(Owner owner) {
        dao.save(owner);
    }

    public void delete(int id) {
        dao.deleteById(id);
    }

    public boolean exists(int id) {
        return dao.existsById(id);
    }
}
