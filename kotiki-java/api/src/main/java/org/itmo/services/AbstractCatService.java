package org.itmo.services;

import org.itmo.enums.Color;
import org.itmo.models.Cat;

import java.util.List;

public interface AbstractCatService {

    List<Cat> getAll();

    Cat get(int id);

    List<Cat> getByBreed(String breed);

    List<Cat> getByColor(Color color);

    void save(Cat cat);

    void delete(int id);

    boolean exists(int id);
}
