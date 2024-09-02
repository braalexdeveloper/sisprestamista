package com.brayanweb.sisprestamistas.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private Date paymentDate;
    private String receiptPayment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private Date createdAt;
    private Date updatedAt;

    public enum PaymentStatus {
        PENDIENTE,
        PROCESANDO,
        COMPLETADO;
    }

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    // Establecer valores por defecto antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = PaymentStatus.PENDIENTE;  // Valor por defecto para status
        }
        createdAt = new Date();  // Fecha y hora actuales
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();  // Actualizar la fecha en cada modificación
    }
}
