package com.brayanweb.sisprestamistas.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentResponse {

    private Long id;
    private Double amount;
    private String receiptPayment;
    private Date createdAt;
    private Long loanId;
    private String userName;
}
