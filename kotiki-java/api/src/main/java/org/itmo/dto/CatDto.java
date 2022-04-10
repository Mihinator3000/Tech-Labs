package org.itmo.dto;

import lombok.Builder;
import lombok.Data;
import org.itmo.enums.Color;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CatDto {

    private int id;

    private String name;

    private LocalDate birthDate;

    private String breed;

    private Color color;

    private Integer ownerId;

    private List<Integer> friendIds;
}
