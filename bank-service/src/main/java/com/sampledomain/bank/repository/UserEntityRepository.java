package com.sampledomain.bank.repository;

import com.sampledomain.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityById(Long userEntityId);
}
