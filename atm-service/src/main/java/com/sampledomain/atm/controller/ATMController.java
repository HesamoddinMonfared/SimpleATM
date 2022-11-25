package com.sampledomain.atm.controller;

import com.sampledomain.atm.utility.PrintOutput;
import com.sampledomain.atm.service.CardEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/V1/atms")
@Slf4j
public class ATMController {
    @Autowired
    private CardEntityService cardEntityService;

    @GetMapping("/cards/enterCard/{cardEntityNumber}")
    public ResponseEntity<String> enterCard(@PathVariable String cardEntityNumber){
        log.info("ATMController::enterCard");
        return new ResponseEntity<>(cardEntityService.enterCard(cardEntityNumber), HttpStatus.OK);
    }

    @GetMapping("/cards/exitCard/{cardEntityNumber}")
    public ResponseEntity<String> exitCard(@PathVariable String cardEntityNumber){
        log.info("ATMController::exitCard");
        return new ResponseEntity<>(cardEntityService.exitCard(cardEntityNumber), HttpStatus.OK);
    }

    @GetMapping("/cards/loginByCardNumberAndPinCode/{cardEntityNumber}/{cardEntityPinCode}")
    public ResponseEntity<PrintOutput> loginByCardNumberAndPinCode(@PathVariable String cardEntityNumber, @PathVariable Short cardEntityPinCode) {
        log.info("ATMController::loginByCardNumberAndPinCode");
        return new ResponseEntity<>(cardEntityService.loginByCardNumberAndPinCode(cardEntityNumber, cardEntityPinCode), HttpStatus.OK);
    }

    @GetMapping("/cards/loginByCardNumberAndFingerprint/{cardEntityNumber}/{cardEntityFingerprint}")
    public ResponseEntity<PrintOutput> loginByCardNumberAndFingerprint(@PathVariable String cardEntityNumber, @PathVariable String cardEntityFingerprint) {
        log.info("ATMController::loginByCardNumberAndFingerprint");
        return new ResponseEntity<>(cardEntityService.loginByCardNumberAndFingerprint(cardEntityNumber, cardEntityFingerprint), HttpStatus.OK);
    }

    @GetMapping("/cards/balance/{cardEntityNumber}")
    public ResponseEntity<PrintOutput> balance(@PathVariable String cardEntityNumber) {
        log.info("ATMController::balance");
        return new ResponseEntity<>(cardEntityService.balance(cardEntityNumber), HttpStatus.OK);
    }

    @PostMapping("/cards/deposit/{cardEntityNumber}/{amount}")
    public ResponseEntity<PrintOutput> deposit(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) {
        log.info("ATMController::deposit");
        return new ResponseEntity<>(cardEntityService.deposit(cardEntityNumber, amount), HttpStatus.CREATED);
    }

    @PostMapping("/cards/withdrawal/{cardEntityNumber}/{amount}")
    public ResponseEntity<PrintOutput> withdrawal(@PathVariable String cardEntityNumber, @PathVariable BigDecimal amount) {
        log.info("ATMController::withdrawal");
        return new ResponseEntity<>(cardEntityService.withdrawal(cardEntityNumber, amount), HttpStatus.CREATED);
    }
}
