package com.sampledomain.bank.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The output of all account related operations of user when using atm. It will be used to create a report to display the result and information of users operations whether in display monitor or in paper.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintOutput {
    /**
     * The date and time of displaying report
     */
    LocalDateTime dateTime;
    /**
     * Users full-name as name-family
     */
    String userFullName;
    /**
     * The card number
     */
    String cardNumber;
    /**
     * The account number related to card number
     */
    String accountNumber;
    /**
     * The amount of money saved in user's account after the operation.
     */
    BigDecimal balance;
}
