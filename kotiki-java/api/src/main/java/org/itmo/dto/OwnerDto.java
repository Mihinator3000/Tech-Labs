package org.itmo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OwnerDto {

    private int id;

    private String name;

    private List<Integer> catIds;
}
