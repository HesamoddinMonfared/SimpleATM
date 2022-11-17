package com.sampledomain.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cardNumber;
    private String pinCode;
    private boolean isLocked;
    private Integer numOfPinFailed;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private AccountEntity accountEntity;
}
