package services;

import enums.Color;
import models.Cat;

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
