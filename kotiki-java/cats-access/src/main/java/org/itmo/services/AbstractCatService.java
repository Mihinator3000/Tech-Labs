package org.itmo.services;

import org.itmo.entities.Cat;
import org.itmo.enums.Color;

import java.util.List;

public interface AbstractCatService {

    List<Cat> getAll();

    Cat get(int id);

    List<Cat> getByBreed(String breed);

    List<Cat> getByColor(Color color);

    List<Cat> getByOwnerId(int ownerId);

    List<Cat> getByBreedAndOwnerId(String breed, int ownerId);

    List<Cat> getByColorAndOwnerId(Color color, int ownerId);

    void save(Cat cat);

    void delete(int id);

    boolean exists(int id);
}
