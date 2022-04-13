package org.itmo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private int id;

    private String username;

    private String password;

    private Integer ownerId;

    private List<String> roles;
}
