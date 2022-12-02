package com.sampledomain.atm.service;

import com.sampledomain.atm.utility.PrintOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CardEntityService {
    @Autowired
    private RestTemplate restTemplate;

    //In order to access bank-service via eureka server.
    private final String apiPath = "https://BANK-SERVICE/api/V1/banks/cards/";

    //In order to access bank-service directly.
    //private final String apiPath = "https://localhost:9001/api/V1/banks/cards/";

    public String enterCard(String cardEntityNumber) {
        try {
            String cardEntity = restTemplate.getForObject(String.format(apiPath + "enterCard/%s", cardEntityNumber), String.class);

            return cardEntity;
        } catch (Exception e) {
            return "error message: " + e.getMessage();
        }
    }

    public String exitCard(String cardEntityNumber) {
        String cardEntity = restTemplate.getForObject(String.format(apiPath + "exitCard/%s", cardEntityNumber), String.class);

        return cardEntity;
    }

    public PrintOutput loginByCardNumberAndPinCode(String cardEntityNumber, Short cardEntityPinCode) {
        PrintOutput cardEntity = restTemplate.getForObject(String.format(apiPath + "loginByCardNumberAndPinCode/%s/%s", cardEntityNumber, cardEntityPinCode), PrintOutput.class);

        return cardEntity;
    }

    public PrintOutput loginByCardNumberAndFingerprint(String cardEntityNumber, String cardEntityFingerprint) {
        PrintOutput cardEntity = restTemplate.getForObject(String.format(apiPath + "loginByCardNumberAndFingerprint/%s/%s", cardEntityNumber, cardEntityFingerprint), PrintOutput.class);

        return cardEntity;
    }

    public PrintOutput balance(String cardNumber) {
        PrintOutput accountEntity = restTemplate.getForObject(String.format(apiPath + "balance/%s", cardNumber), PrintOutput.class);

        return accountEntity/*.getBalance()*/;
    }

    public PrintOutput deposit(String cardNumber, BigDecimal amount) {
        PrintOutput accountEntity = restTemplate.getForObject(String.format(apiPath + "deposit/%s/%s", cardNumber, amount), PrintOutput.class);

        return accountEntity;
    }

    public PrintOutput withdrawal(String cardNumber, BigDecimal amount) {
        PrintOutput accountEntity = restTemplate.getForObject(String.format(apiPath + "withdrawal/%s/%s", cardNumber, amount), PrintOutput.class);

        return accountEntity;
    }
}
