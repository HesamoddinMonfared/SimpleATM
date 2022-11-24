package com.sampledomain.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * {@link AccountEntity belongs to UserEntity object.
 * Each UserEntity object can have multiple accounts,
 * as well as each AccountEntity object has at most one CardEntity object.}
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    /**
     * The account primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Account number containing mixture of digits and seperated dash-line.
     */
    private String accountNumber;


    /**
     * Amount of money saved for the owner of account.
     */
    private BigDecimal balance;


    /**
     * Id of branch to find branch name.
     */
    private Long branchId;

    /**
     * it's used as user entity foreign key
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_entity_id", nullable = true, referencedColumnName = "id")//"id": is user table "id" column
    @JsonIgnore
    private UserEntity userEntity;

    /**
     * establish one-to-one relation with card entity
     */
    @OneToOne(mappedBy = "accountEntity")
    @JsonIgnore
    private CardEntity cardEntity;
}
