package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findAccountEntityById(Long accountEntityId);
    List<AccountEntity> findByUserEntityId(Long userEntityId);
    @Transactional
    void deleteByUserEntityId(Long userEntityId);//delete all accounts for user, transactional
    List<AccountEntity> findByBranchId(Long branchId);
}
