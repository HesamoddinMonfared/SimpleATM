package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
}
