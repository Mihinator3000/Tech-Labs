package services;

import models.Owner;

import java.util.List;

public interface AbstractOwnerService {

    List<Owner> getAll();

    Owner get(int id);

    void save(Owner owner);

    void delete(int id);

    boolean exists(int id);
}
