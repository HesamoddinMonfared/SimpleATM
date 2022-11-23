package com.sampledomain.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nationalCode;
    private String name;
    private String family;
    private String mobile;
    private String fingerprint;

    public String fullName() {
        return String.format("%s %s", name, family);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AccountEntity> accountEntities;
}
