package com.brayanweb.sisprestamistas.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanResponse {

    private Long id;
    private Double amount;
    private Integer interestRate;
    private Integer term;
    private String status;
    private Date createdAt;
    private String clientName;
}
