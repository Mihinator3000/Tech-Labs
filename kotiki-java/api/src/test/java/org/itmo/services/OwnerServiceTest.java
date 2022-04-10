package org.itmo.services;

import org.itmo.dao.OwnerDao;
import org.itmo.models.Cat;
import org.itmo.models.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OwnerServiceTest {

    private AbstractOwnerService service;
    private OwnerDao dao;

    @BeforeEach()
    public void setup() {
        dao = mock(OwnerDao.class);
        service = new OwnerService(dao);
    }

    @Test
    public void testGetAll() {
        var owner1 = Owner.builder().id(1).name("Name1").build();
        var owner2 = Owner.builder().id(2).name("Name2").build();
        var owners = List.of(owner1, owner2);

        when(dao.findAll()).thenReturn(owners);

        List<Owner> result = service.getAll();
        verify(dao).findAll();

        assertEquals(2, result.size());

        assertEquals(owner1, result.get(0));

        assertEquals(owner2, result.get(1));
    }

    @Test
    public void testGet() {
        var owner = Owner.builder().id(1).name("Name").build();

        when(dao.findById(1)).thenReturn(Optional.of(owner));

        Owner result = service.get(1);

        verify(dao).findById(anyInt());

        assertEquals(owner, result);
    }

    @Test
    public void testSave() {
        var owner = Owner.builder()
                .id(1)
                .name("SomeName")
                .birthDate(LocalDate.now())
                .cats(List.of(new Cat("Cat")))
                .build();

        service.save(owner);
        verify(dao, times(1)).save(any());
    }

    @Test
    public void testDelete() {
        service.delete(1);
        verify(dao, times(1)).deleteById(anyInt());
    }
}
