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
    private Short pinCode;
    private Integer numOfFailedTimes;//todo mhk: This field makes redundancy
    private Boolean isInAtm;
    private PasswordType passwordType;//0:pin, 1:fingerprint

    public Boolean isLocked(){
        return numOfFailedTimes >= 3;
    }

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "account_entity_id", nullable = true, referencedColumnName = "id")//"id": is card table "id" column
    @JsonIgnore
    private AccountEntity accountEntity;
}
