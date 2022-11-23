package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.AccountEntityService;
import com.sampledomain.bank.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/V1/banks")
@Slf4j
public class AccountEntityController {
    @Autowired
    private AccountEntityService accountEntityService;

    @Autowired
    private UserEntityService userEntityService;

//    @GetMapping("/accounts/{accountEntityId}")
//    public ResponseEntity<AccountEntity> findAccountEntityById(@PathVariable Long accountEntityId) throws ResourceNotFoundException {
//        log.info("AccountEntityController::findAccountEntityById");
//        AccountEntity accountEntity = accountEntityService.findAccountEntityById(accountEntityId)
//                .orElseThrow(() -> new ResourceNotFoundException("Not found Account with id: " + accountEntityId));
//        return new ResponseEntity<>(accountEntity, HttpStatus.CREATED);
//    }

    @GetMapping("/accounts/{branchId}")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(@PathVariable(required = false) Long branchId) {
        List<AccountEntity> accountEntities = new ArrayList<>();

        if (branchId == null) {
            accountEntities.addAll(accountEntityService.findAll());
        } else {
            accountEntities.addAll(accountEntityService.findByBranchId(branchId));
        }

        if (accountEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(accountEntities, HttpStatus.OK);
        }
    }

    @PostMapping("/users/{userEntityNationalCode}/accounts")
    public ResponseEntity<AccountEntity> saveAccount(@PathVariable String userEntityNationalCode, @RequestBody AccountEntity accountEntity) throws ResourceNotFoundException {
        UserEntity userEntity;
        if (!Objects.equals(userEntityNationalCode, "0")) {
            userEntity = userEntityService.findUserEntityByNationalCode(userEntityNationalCode);
            //.orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + userEntityNationalCode));
        } else {
            userEntity = userEntityService.saveUserEntity(accountEntity.getUserEntity());
        }

        accountEntity.setUserEntity(userEntity);
        accountEntityService.save(accountEntity);
        return new ResponseEntity<>(accountEntity, HttpStatus.CREATED);
    }
}
