package com.sampledomain.atm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/V1/atms")
@Slf4j
public class ATMController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login/{cardNumber}/{pinCode}")
    public ResponseEntity<String> loginWithPinCode(@PathVariable String cardNumber,
                                                   @PathVariable String pinCode){

        log.info("ATMController::loginWithPinCode");
        return null;
    }

    @GetMapping("/getBalance/{cardNumber}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String cardNumber){
        return null;
    }

}
