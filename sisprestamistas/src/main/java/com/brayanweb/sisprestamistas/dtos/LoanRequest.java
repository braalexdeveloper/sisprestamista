package com.brayanweb.sisprestamistas.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRequest {

    @NotNull(message = "El monto es requerido")
    private Double amount;

    @NotNull(message = "El interes es requerido")
    private Integer interestRate;

    @NotNull(message = "El plazo del prestamo es requerido")
    private Integer term;

    @NotNull(message = "El id del usuario es requerido")
    private Long userId;

}
