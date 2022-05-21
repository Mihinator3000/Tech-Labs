package org.itmo.services;


import org.itmo.dto.CatDto;
import org.itmo.enums.Color;

import java.util.List;

public interface AbstractCatService {

    List<CatDto> getAll();

    CatDto get(int id);

    List<CatDto> getByBreed(String breed);

    List<CatDto> getByColor(Color color);

    List<CatDto> getByOwnerId(int ownerId);

    List<CatDto> getByBreedAndOwnerId(String breed, int ownerId);

    List<CatDto> getByColorAndOwnerId(Color color, int ownerId);

    void save(CatDto cat);

    void delete(int id);

    boolean exists(int id);
}
