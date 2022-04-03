package dao;

import enums.Color;
import models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatDao extends JpaRepository<Cat, Integer> {

    List<Cat> findByBreed(@Param("breed") String breed);

    List<Cat> findByColor(@Param("color") Color color);
}
