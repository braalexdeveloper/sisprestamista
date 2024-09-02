package com.brayanweb.sisprestamistas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponse {

    private Long id;
    private String name;
    private String lastName;
    private Integer dni;
    private String address;
    private String policeRecord;
    private String photo;
}
