package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.repository.UserEntityRepository;
import com.sampledomain.bank.service.AccountEntityService;
import com.sampledomain.bank.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V1/banks")
@Slf4j
public class AccountEntityController {

    @Autowired
    private AccountEntityService accountEntityService;

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/accounts/{accountEntityId}")
    public ResponseEntity<AccountEntity> findAccountEntityById(@PathVariable Long accountEntityId) throws ResourceNotFoundException {
        log.info("AccountEntityController::findAccountEntityById");
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

    @PostMapping("/users/{userEntityId}/accounts")
    public ResponseEntity<AccountEntity> createAccount(@PathVariable Long userEntityId, @RequestBody AccountEntity accountEntity) throws ResourceNotFoundException {
        UserEntity userEntity  = userEntityService.findUserEntityById(userEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + userEntityId));
        accountEntity.setUserEntity(userEntity);
        accountEntityService.save(accountEntity);
        return new ResponseEntity<>(accountEntity, HttpStatus.CREATED);
    }
}
