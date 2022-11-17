package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.AccountEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class AccountEntityController {

    @Autowired
    private AccountEntityService accountEntityService;

    @GetMapping("/accounts/{accountEntityId}")
    public ResponseEntity<AccountEntity> findAccountEntityById(@PathVariable Long accountEntityId) throws ResourceNotFoundException {
        AccountEntity accountEntity = accountEntityService.findAccountEntityById(accountEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Account with id: " + accountEntityId));
        return new ResponseEntity<>(accountEntity, HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{branchName}")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(@PathVariable(required = false) String branchName){
        List<AccountEntity> accountEntities = new ArrayList<>();

        if(branchName == null)
            accountEntityService.findAll().forEach(accountEntities::add);
        else
            accountEntityService.findByBranchNameContaining(branchName).forEach(accountEntities::add);

        if(accountEntities.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(accountEntities, HttpStatus.OK);
    }
}
