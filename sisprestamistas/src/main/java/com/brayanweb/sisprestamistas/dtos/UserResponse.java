package com.brayanweb.sisprestamistas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String name;
    private String lastName;
    private String email;
    private String roleName;
}
