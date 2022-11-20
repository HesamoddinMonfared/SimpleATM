package com.sampledomain.atm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {
    private Long id;
    private String cardNumber;
    private Short pinCode;
    private String fingerPrint;
    private boolean isLocked;
    private Integer numOfFailedTimes;//todo mhk: This field makes redundancy
}
