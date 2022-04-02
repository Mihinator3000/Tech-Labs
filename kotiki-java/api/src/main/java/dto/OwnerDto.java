package dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class OwnerDto {

    private int id;

    private String name;

    private List<Integer> catIds;
}
