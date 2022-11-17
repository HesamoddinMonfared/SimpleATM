package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardEntityRepository extends JpaRepository<CardEntity, Long> {

    Optional<CardEntity> findCardEntityById(Long cardEntityId);


}
