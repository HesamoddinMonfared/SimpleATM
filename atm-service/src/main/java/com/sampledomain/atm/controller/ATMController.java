package com.sampledomain.atm.controller;

import com.sampledomain.atm.entity.AccountEntity;
import com.sampledomain.atm.entity.CardEntity;
import com.sampledomain.atm.service.CardEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/V1/atms")
@Slf4j
public class ATMController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CardEntityService cardEntityService;

    @GetMapping("/cards/CardNumber/{cardEntityNumber}")
    public ResponseEntity<CardEntity> findByCardNumber(@PathVariable String cardEntityNumber){
        log.info("ATMController::findByCardNumber");
        return new ResponseEntity<>(cardEntityService.findByCardNumber(cardEntityNumber), HttpStatus.OK);
    }

    @GetMapping("/cards/CardNumberAndPinCode/{cardEntityNumber}/{cardEntityPinCode}")
    public ResponseEntity<CardEntity> findByCardNumberAndPinCode(@PathVariable Long cardEntityNumber, @PathVariable Short cardEntityPinCode) {
        log.info("ATMController::findByCardNumberAndPinCode");
        return new ResponseEntity<>(cardEntityService.findByCardNumberAndPinCode(cardEntityNumber, cardEntityPinCode), HttpStatus.OK);
    }

    @GetMapping("/cards/CardNumberAndFingerprint/{cardEntityNumber}/{cardEntityFingerprint}")
    public ResponseEntity<CardEntity> findByCardNumberAndFingerprint(@PathVariable Long cardEntityNumber, @PathVariable String cardEntityFingerprint) {
        log.info("ATMController::findByCardNumberAndFingerprint");
        return new ResponseEntity<>(cardEntityService.findByCardNumberAndFingerprint(cardEntityNumber, cardEntityFingerprint), HttpStatus.OK);
    }

    @GetMapping("/cards/balance/{cardEntityNumber}")
    public ResponseEntity<AccountEntity> findBalance(@PathVariable String cardEntityNumber) {
        log.info("ATMController::findBalance");
        return new ResponseEntity<>(cardEntityService.findBalance(cardEntityNumber), HttpStatus.OK);
    }

    @PostMapping("/cards/deposit/{cardEntityNumber}/{amount}")
    public ResponseEntity<AccountEntity> doDeposit(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) {
        log.info("ATMController::doDeposit");
        return new ResponseEntity<>(cardEntityService.doDeposit(cardEntityNumber, amount), HttpStatus.CREATED);
    }

    @PostMapping("/cards/withdrawal/{cardEntityNumber}/{amount}")
    public ResponseEntity<AccountEntity> doWithdrawal(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) {
        log.info("ATMController::doWithdrawal");
        return new ResponseEntity<>(cardEntityService.doWithdrawal(cardEntityNumber, amount), HttpStatus.CREATED);
    }
}
