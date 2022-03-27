import dao.OwnerDao;
import models.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.OwnerService;

import java.util.ArrayList;
import java.util.List;

public class OwnerServiceTest {

    private OwnerService service;
    private OwnerDao dao;

    @BeforeEach
    public void setup() {
        dao = Mockito.mock(OwnerDao.class);
        service = new OwnerService(dao);
    }

    @Test
    public void findAll() {
        var owners = new ArrayList<Owner>();
        owners.add(new Owner("Name1"));
        owners.add(new Owner("Name2"));

        Mockito.when(dao.getAll()).thenReturn(owners);

        List<Owner> result = service.getAll();
        Mockito.verify(dao).getAll();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Name1", result.get(0).getName());

        Assertions.assertEquals("Name2", result.get(1).getName());
    }
}
