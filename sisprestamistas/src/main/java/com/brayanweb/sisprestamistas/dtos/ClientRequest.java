package com.brayanweb.sisprestamistas.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {

    @NotNull(message = "El dni es requerido")
    private Integer dni;

    @NotNull(message = "La dirección es requerido")
    private String address;

    @NotNull(message = "Los antecedentes penales es requerido")
    private String policeRecord;

    @NotNull(message = "La foto es requerida")
    private String photo;

    @NotNull(message = "El id del usuario es requerido")
    private Long userId;
}
