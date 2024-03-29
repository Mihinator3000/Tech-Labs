package org.itmo.services;

import org.itmo.dao.OwnerDao;
import lombok.RequiredArgsConstructor;
import org.itmo.entities.Owner;
import org.itmo.tools.DalException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService implements AbstractOwnerService {

    private final OwnerDao dao;

    public List<Owner> getAll() {
        return dao.findAll();
    }

    public Owner get(int id) {
        Optional<Owner> result = dao.findById(id);
        if (result.isEmpty())
            throw new DalException("No owner with such id found");

        return result.get();
    }

    public Owner getByCatId(int catId) {
        return getAll()
                .stream()
                .filter(o -> o.getCats().stream().anyMatch(c -> c.getId() == catId))
                .findFirst()
                .orElse(null);
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
