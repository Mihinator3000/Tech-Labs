package services;

import dao.CatDao;
import dao.Dao;
import enums.Color;
import models.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CatServiceTest {

    private CatService service;
    private Dao<Cat> dao;

    @BeforeEach
    public void setup() {
        dao = mock(CatDao.class);
        service = new CatService(dao);
    }

    @Test
    public void testGetAll() {
        var cat1 = Cat.builder().id(1).name("Cat").build();
        var cat2 = Cat.builder().id(2).name("Also A Cat").build();
        var cats = List.of(cat1, cat2);

        when(dao.getAll()).thenReturn(cats);

        List<Cat> result = service.getAll();
        verify(dao).getAll();

        assertEquals(2, result.size());

        assertEquals(cat1, result.get(0));

        assertEquals(cat2, result.get(1));
    }

    @Test
    public void testGet() {
        var cat = Cat.builder().id(1).name("Name").build();

        when(dao.get(1)).thenReturn(cat);

        Cat result = service.get(1);

        verify(dao).get(anyInt());

        assertEquals(cat, result);
    }

    @Test
    public void testAdd() {
        var cat = Cat.builder()
                .id(1)
                .name("Name")
                .friends(List.of(new Cat("Another Cat")))
                .build();

        service.add(cat);
        verify(dao, times(1)).add(any());
    }

    @Test
    public void testUpdate() {
        var cat = new Cat("Kitty");

        cat.setColor(Color.BLUE);

        service.update(cat);
        verify(dao, times(1)).update(any());
    }

    @Test
    public void testDelete() {
        service.delete(1);
        verify(dao, times(1)).delete(anyInt());
    }
}
