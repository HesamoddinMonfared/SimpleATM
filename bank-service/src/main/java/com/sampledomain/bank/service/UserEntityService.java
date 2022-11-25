package com.sampledomain.bank.service;

import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    public UserEntity saveUserEntity(UserEntity userEntity) throws ResourceNotFoundException {
        try {
            return userEntityRepository.save(userEntity);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in UserEntityService::saveUserEntity: " + e.getMessage());
        }
    }

    public Optional<UserEntity> findUserEntityByNationalCode(String userEntityNationalCode) throws ResourceNotFoundException {
        try {
            return userEntityRepository.findByNationalCode(userEntityNationalCode);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in UserEntityService::findUserEntityByNationalCode: " + e.getMessage());
        }
    }

    public List<UserEntity> findAllUsers() throws ResourceNotFoundException {
        try {
            return userEntityRepository.findAll();
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in UserEntityService::findAllUsers: " + e.getMessage());
        }
    }
}
