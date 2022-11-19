package com.sampledomain.cloud.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/bankServiceFallBack")
    public String backServiceFallBackMethod(){
        return "Bank service does not respond, try again late.";
    }

    @GetMapping("/atmServiceFallBack")
    public String atmServiceFallBackMethod(){
        return "ATM service does not respond, try again late.";
    }
}
