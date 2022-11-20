package com.sampledomain.atm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long branchId;
}
