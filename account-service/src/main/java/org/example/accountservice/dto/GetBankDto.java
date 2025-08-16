package org.example.accountservice.dto;

import lombok.Data;

@Data
public class GetBankDto {
    private Long id;
    private String name;
    private String address;
}
