package com.brayanweb.sisprestamistas.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotNull(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "El apellido es requerido")
    private String lastName;

    @NotNull(message = "El email es requerido")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "La contraseña es requerida")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "El rol es requerido")
    private Long roleId;

}
