package org.itmo.services;

import org.itmo.entities.Owner;

import java.util.List;

public interface AbstractOwnerService {

    List<Owner> getAll();

    Owner get(int id);

    Owner getByCatId(int catId);

    void save(Owner owner);

    void delete(int id);

    boolean exists(int id);
}
