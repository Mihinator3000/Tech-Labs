package services;

import dao.Dao;
import dao.OwnerDao;
import models.Cat;
import models.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OwnerServiceTest {

    private OwnerService service;
    private Dao<Owner> dao;

    @BeforeEach
    public void setup() {
        dao = mock(OwnerDao.class);
        service = new OwnerService(dao);
    }

    @Test
    public void testGetAll() {
        var owner1 = Owner.builder().id(1).name("Name1").build();
        var owner2 = Owner.builder().id(2).name("Name2").build();
        var owners = List.of(owner1, owner2);

        when(dao.getAll()).thenReturn(owners);

        List<Owner> result = service.getAll();
        verify(dao).getAll();

        assertEquals(2, result.size());

        assertEquals(owner1, result.get(0));

        assertEquals(owner2, result.get(1));
    }

    @Test
    public void testGet() {
        var owner = Owner.builder().id(1).name("Name").build();

        when(dao.get(1)).thenReturn(owner);

        Owner result = service.get(1);

        verify(dao).get(anyInt());

        assertEquals(owner, result);
    }

    @Test
    public void testAdd() {
        var owner = Owner.builder()
                .id(1)
                .name("SomeName")
                .birthDate(LocalDate.now())
                .cats(List.of(new Cat("Cat")))
                .build();

        service.add(owner);
        verify(dao, times(1)).add(any());
    }

    @Test
    public void testUpdate() {
        var owner = new Owner("Namely name");

        owner.setBirthDate(LocalDate.now(Clock.systemUTC()));

        service.update(owner);
        verify(dao, times(1)).update(any());
    }

    @Test
    public void testDelete() {
        service.delete(1);
        verify(dao, times(1)).delete(anyInt());
    }
}
