package com.sampledomain.atm.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintOutput {
    LocalDateTime dateTime;
    String userFullName;
    String cardNumber;
    String accountNumber;
    BigDecimal balance;
}
