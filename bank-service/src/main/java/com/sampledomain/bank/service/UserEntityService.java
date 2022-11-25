package com.sampledomain.bank.service;

import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    public UserEntity saveUserEntity(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public Optional<UserEntity> findUserEntityByNationalCode(String userEntityNationalCode) {
        return userEntityRepository.findByNationalCode(userEntityNationalCode);
    }

    public List<UserEntity> findAllUsers() {
        return userEntityRepository.findAll();
    }
}
