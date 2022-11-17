package com.sampledomain.bank.service;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.repository.AccountEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountEntityService {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    public Optional<AccountEntity> findAccountEntityById(Long accountEntityId) {
        return accountEntityRepository.findAccountEntityById(accountEntityId);
    }

    public List<AccountEntity> findAll(){
        return accountEntityRepository.findAll();
    }

    public List<AccountEntity> findByBranchNameContaining(String branchName){
        return accountEntityRepository.findByBranchNameContaining(branchName);
    }
}
