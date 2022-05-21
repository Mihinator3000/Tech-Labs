package org.itmo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itmo.enums.Color;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatDto {

    private int id;

    private String name;

    private LocalDate birthDate;

    private String breed;

    private Color color;

    private List<Integer> friendIds;
}
