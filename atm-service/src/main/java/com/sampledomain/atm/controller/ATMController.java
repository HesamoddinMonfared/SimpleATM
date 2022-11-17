package com.sampledomain.atm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/V1")
@Slf4j
public class ATMController {

    @GetMapping("/atm/login/{cardNumber}/{pinCode}")
    public ResponseEntity<String> loginWithPinCode(@PathVariable String cardNumber,
                                                   @PathVariable String pinCode){
        log.info("ATMController::loginWithPinCode");
        return null;
    }

    @GetMapping("/atm/getBalance/{cardNumber}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String cardNumber){
        return null;
    }
}
