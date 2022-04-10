package org.itmo.services;

import org.itmo.dao.CatDao;
import org.itmo.models.Cat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CatServiceTest {

    @Autowired
    private AbstractCatService service;

    @MockBean
    private CatDao dao;


    @Test
    public void testGetAll() {
        var cat1 = Cat.builder().id(1).name("Cat").build();
        var cat2 = Cat.builder().id(2).name("Also A Cat").build();
        var cats = List.of(cat1, cat2);

        when(dao.findAll()).thenReturn(cats);

        List<Cat> result = service.getAll();
        verify(dao).findAll();

        assertEquals(2, result.size());

        assertEquals(cat1, result.get(0));

        assertEquals(cat2, result.get(1));
    }

    @Test
    public void testGet() {
        var cat = Cat.builder().id(1).name("Name").build();

        when(dao.findById(1)).thenReturn(Optional.of(cat));

        Cat result = service.get(1);

        verify(dao).findById(anyInt());

        assertEquals(cat, result);
    }

    @Test
    public void testSave() {
        var cat = Cat.builder()
                .id(1)
                .name("Name")
                .friends(List.of(new Cat("Another Cat")))
                .build();

        service.save(cat);
        verify(dao, times(1)).save(any());
    }

    @Test
    public void testDelete() {
        service.delete(1);
        verify(dao, times(1)).deleteById(anyInt());
    }
}
