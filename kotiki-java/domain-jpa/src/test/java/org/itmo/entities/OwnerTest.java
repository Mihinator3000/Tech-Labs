package org.itmo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OwnerTest {

    @Test
    public void testEqualsById() {
        var owner1 = Owner.builder()
                .id(1)
                .name("Name")
                .build();

        var owner2 = Owner.builder()
                .id(1)
                .name("Other Name")
                .build();

        assertEquals(owner1, owner2);
    }

    @Test
    public void createOwnerWithoutAName_ThrowException() {
        assertThrows(NullPointerException.class, () ->
                Owner.builder().build());
    }
}
