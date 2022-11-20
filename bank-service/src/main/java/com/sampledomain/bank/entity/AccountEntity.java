package com.sampledomain.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private UserEntity userEntity;
}
