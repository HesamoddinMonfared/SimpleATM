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
import java.util.Optional;

/**
 * {@link AccountEntityController class manages rest-api requests for the owner of account.}
 */
@RestController
@RequestMapping("/api/V1/banks")
@Slf4j
public class AccountEntityController {
    /**
     * Service object for account class
     */
    @Autowired
    private AccountEntityService accountEntityService;

    /**
     * Service object for user class
     */
    @Autowired
    private UserEntityService userEntityService;

    /**
     * @param branchId The id of branch to find accounts related to specified branch name.
     * @return list of accounts related to branchId
     */
    @GetMapping("/accounts/{branchId}")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(@PathVariable(required = false) Long branchId) throws ResourceNotFoundException {
        try {
            List<AccountEntity> accountEntities = new ArrayList<>();

            if (branchId == null) {
                accountEntities.addAll(accountEntityService.findAll());
            } else {
                accountEntities.addAll(accountEntityService.findByBranchId(branchId));
            }

            if (accountEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accountEntities, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * @param userEntityNationalCode The nationalCode of account's owner to establish connection between user and coming account.
     * @param accountEntity The account information to save in database.
     * @return The whole information about account containing the related user info.
     * @throws ResourceNotFoundException
     */
    @PostMapping("/users/{userEntityNationalCode}/accounts")
    public ResponseEntity<Object> saveAccount(@PathVariable String userEntityNationalCode, @RequestBody AccountEntity accountEntity) throws ResourceNotFoundException {
        try {
            if (accountEntityService.findAccountEntityByNumber(accountEntity.getAccountNumber()).isPresent()) {
                return new ResponseEntity<>("account exist already.", HttpStatus.ALREADY_REPORTED);
            }

            Optional<UserEntity> userEntity = userEntityService.findUserEntityByNationalCode(userEntityNationalCode);
            if (userEntity.isEmpty()) {
                return new ResponseEntity<>("User with specified national-code not exists.", HttpStatus.EXPECTATION_FAILED);
            }

            accountEntity.setUserEntity(userEntity.get());
            accountEntityService.save(accountEntity);
            return new ResponseEntity<>(accountEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("message in AccountEntityController::saveAccount: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
