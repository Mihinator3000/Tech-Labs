package org.itmo.services;

import org.itmo.dto.OwnerDto;

import java.util.List;

public interface AbstractOwnerService {

    List<OwnerDto> getAll();

    OwnerDto get(int id);

    OwnerDto getByCatId(int catId);

    void save(OwnerDto owner);

    void delete(int id);

    boolean exists(int id);
}
