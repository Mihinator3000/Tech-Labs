package org.itmo.dao;

import org.itmo.enums.Color;
import org.itmo.models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatDao extends JpaRepository<Cat, Integer> {

    List<Cat> findByBreed(String breed);

    List<Cat> findByColor(Color color);
}
