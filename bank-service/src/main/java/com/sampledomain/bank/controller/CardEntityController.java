package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.AccountEntityService;
import com.sampledomain.bank.service.CardEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/V1/banks")
public class CardEntityController {
    @Autowired
    private CardEntityService cardEntityService;
    @Autowired
    private AccountEntityService accountEntityService;

    @GetMapping("/cards/cardNumber/{cardEntityNumber}")
    public ResponseEntity<CardEntity> findByCardNumber(@PathVariable String cardEntityNumber) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.findByCardNumber(cardEntityNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @GetMapping("/cards/cardNumberAndPinCode/{cardEntityNumber}/{cardEntityPinCode}")
    public ResponseEntity<CardEntity> findByCardNumberAndPinCode(@PathVariable String cardEntityNumber, @PathVariable Short cardEntityPinCode) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.findByCardNumberAndPinCode(cardEntityNumber, cardEntityPinCode)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @GetMapping("/cards/cardNumberAndFingerprint/{cardEntityNumber}/{cardEntityFingerprint}")
    public ResponseEntity<CardEntity> findByCardNumberAndFingerprint(@PathVariable String cardEntityNumber, @PathVariable String cardEntityFingerPrint) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.findByCardNumberAndFingerprint(cardEntityNumber, cardEntityFingerPrint)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @GetMapping("/cards/balance/{cardEntityNumber}")
    public ResponseEntity<BigDecimal> findBalance(@PathVariable String cardEntityNumber) throws ResourceNotFoundException {
        BigDecimal bigDecimal = cardEntityService.findBalance(cardEntityNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(bigDecimal, HttpStatus.OK);
    }

    @PostMapping("/cards/deposit/{cardEntityNumber}/{amount}")
    public ResponseEntity<CardEntity> doDeposit(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.doDeposit(cardEntityNumber, amount)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @PostMapping("/cards/withdrawal/{cardEntityNumber}/{amount}")
    public ResponseEntity<CardEntity> doWithdrawal(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.doDeposit(cardEntityNumber, amount)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Card %s not found.", cardEntityNumber)));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    /*@GetMapping("/cards/{cardEntityId}")
    public ResponseEntity<CardEntity> findCardEntityById(@PathVariable Long cardEntityId) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.findCardEntityById(cardEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Card with id: " + cardEntityId));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountEntityId}/cards/")
    public ResponseEntity<CardEntity> createCard(@PathVariable Long accountEntityId,
                                                 @RequestBody CardEntity cardEntity) throws ResourceNotFoundException {
        AccountEntity accountEntity = accountEntityService.findAccountEntityById(accountEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found account with id: " + accountEntityId));
        cardEntity.setAccountEntity(accountEntity);
        cardEntityService.save(cardEntity);
        return new ResponseEntity<>(cardEntity, HttpStatus.CREATED);
    }*/
}