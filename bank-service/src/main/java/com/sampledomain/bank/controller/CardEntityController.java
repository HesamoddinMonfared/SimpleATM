package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.helper.PrintOutput;
import com.sampledomain.bank.service.AccountEntityService;
import com.sampledomain.bank.service.CardEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/V1/banks")
@Slf4j
public class CardEntityController {
    @Autowired
    private CardEntityService cardEntityService;
    @Autowired
    private AccountEntityService accountEntityService;

    @GetMapping("/cards/enterCard/{cardEntityNumber}")
    public ResponseEntity<String> enterCard(@PathVariable String cardEntityNumber) {
        var message = cardEntityService.verifyEnteredCardByCardNumber(cardEntityNumber);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/cards/exitCard/{cardEntityNumber}")
    public ResponseEntity<String> exitCard(@PathVariable String cardEntityNumber) {
        var message = cardEntityService.verifyExitedCardByCardNumber(cardEntityNumber);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/cards/loginByCardNumberAndPinCode/{cardEntityNumber}/{cardEntityPinCode}")
    public ResponseEntity<String> loginByCardNumberAndPinCode(@PathVariable String cardEntityNumber, @PathVariable Short cardEntityPinCode) throws ResourceNotFoundException {
        var message = cardEntityService.loginByCardNumberAndPinCode(cardEntityNumber, cardEntityPinCode);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/cards/loginByCardNumberAndFingerprint/{cardEntityNumber}/{cardEntityFingerprint}")
    public ResponseEntity<String> loginByCardNumberAndFingerprint(@PathVariable String cardEntityNumber, @PathVariable String cardEntityFingerprint) throws ResourceNotFoundException {
        String message = cardEntityService.loginByCardNumberAndFingerprint(cardEntityNumber, cardEntityFingerprint);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/cards/balance/{cardEntityNumber}")
    public ResponseEntity<PrintOutput> balance(@PathVariable String cardEntityNumber) throws ResourceNotFoundException {
        PrintOutput print = cardEntityService.findBalance(cardEntityNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(print, HttpStatus.OK);
    }

    @PostMapping("/cards/deposit/{cardEntityNumber}/{amount}")
    public ResponseEntity<PrintOutput> deposit(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) throws ResourceNotFoundException {
        PrintOutput print = cardEntityService.doDeposit(cardEntityNumber, amount)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(print, HttpStatus.OK);
    }

    @PostMapping("/cards/withdrawal/{cardEntityNumber}/{amount}")
    public ResponseEntity<PrintOutput> withdrawal(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) throws ResourceNotFoundException {
        PrintOutput print = cardEntityService.doWithdrawal(cardEntityNumber, amount)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(print, HttpStatus.OK);
    }

    @PostMapping("/cards/accounts/{accountEntityId}")
    public ResponseEntity<Object> saveCard(@PathVariable Long accountEntityId,
                                               @RequestBody CardEntity cardEntity) {
        try {
            if (cardEntityService.findCardByCardNumber(cardEntity.getCardNumber()).isPresent()) {
                return new ResponseEntity<>("Card exists already.", HttpStatus.ALREADY_REPORTED);
            }

            Optional<AccountEntity> accountEntity = accountEntityService.findAccountEntityById(accountEntityId);
            if (accountEntity.isEmpty()) {
                return new ResponseEntity<>("Account with specified account id not exists.", HttpStatus.NOT_FOUND);
            }

            cardEntity.setAccountEntity(accountEntity.get());
            cardEntityService.save(cardEntity);
            return new ResponseEntity<>(cardEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(cardEntity, HttpStatus.BAD_REQUEST);
        }
    }
}