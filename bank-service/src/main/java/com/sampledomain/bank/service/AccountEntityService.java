package com.sampledomain.bank.service;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.repository.AccountEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountEntityService {
    @Autowired
    private AccountEntityRepository accountEntityRepository;

    public Optional<AccountEntity> findAccountEntityById(Long accountEntityId) throws ResourceNotFoundException {
        try {
            return accountEntityRepository.findAccountEntityById(accountEntityId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::findAccountEntityById: " + e.getMessage());
        }
    }

    public Optional<AccountEntity> findAccountEntityByNumber(String accountEntityNumber) throws ResourceNotFoundException {
        try {
            return accountEntityRepository.findByAccountNumber(accountEntityNumber);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::findAccountEntityByNumber: " + e.getMessage());
        }
    }

    public List<AccountEntity> findAll(){
        return accountEntityRepository.findAll();
    }

    public List<AccountEntity> findByBranchId(Long branchId) throws ResourceNotFoundException {
        try {
            return accountEntityRepository.findByBranchId(branchId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::findByBranchId: " + e.getMessage());
        }
    }

    public AccountEntity save(AccountEntity accountEntity) throws ResourceNotFoundException {
        try {
            return accountEntityRepository.save(accountEntity);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::save: " + e.getMessage());
        }
    }
}
