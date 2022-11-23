package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardEntityRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findCardEntityById(Long cardEntityId);
    Optional<CardEntity> findByCardNumber(String cardEntityNumber);

    //@Modifying
    //@Query("update AccountEntity ae set ae.balance = ae.balance - :amount where ae.balance >= :amount and ae.id = :id")
    //Optional<CardEntity> doWithdrawal(@Param("cardNumber") String cardNumber, @Param("amount") BigDecimal amount);
}
