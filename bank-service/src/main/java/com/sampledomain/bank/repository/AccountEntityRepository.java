package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * JpaRepository to access AccountEntity related table in database to do crud operations.
 */
@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
    /**
     * @param accountEntityId Find the account record by it's id.
     * @return Account table record.
     */
    Optional<AccountEntity> findAccountEntityById(Long accountEntityId);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    /**
     * @param branchId Find accounts related to specified branch by branchId.
     * @return List of account records by their specified branchId.
     */
    List<AccountEntity> findByBranchId(Long branchId);
}
