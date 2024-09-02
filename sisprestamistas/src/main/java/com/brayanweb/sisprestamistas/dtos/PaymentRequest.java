package com.brayanweb.sisprestamistas.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "La cuota es requerida")
    @DecimalMin(value = "0.0", inclusive = false, message = "La cuota debe ser un número positivo")
    private Double amount;

    @NotNull(message = "La imagen de boucher es requerida")
    private MultipartFile receiptPayment;

    @NotNull(message = "El id del prestamo es requerido")
    private Long loanId;
}
