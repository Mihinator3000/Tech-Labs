package dto;

import enums.Color;
import lombok.*;

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
