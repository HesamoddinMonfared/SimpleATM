package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityById(Long userEntityId);
    Optional<UserEntity> findByNationalCode(String nationalCode);
}
