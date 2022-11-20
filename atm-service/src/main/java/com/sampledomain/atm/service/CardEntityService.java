package com.sampledomain.atm.service;

import com.sampledomain.atm.entity.AccountEntity;
import com.sampledomain.atm.entity.CardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CardEntityService {
    @Autowired
    private RestTemplate restTemplate;

    private final String apiPath = "http://BANK-SERVICE/api/V1/banks/cards/";

    public CardEntity findByCardNumber(String cardEntityNumber) {
        CardEntity cardEntity = restTemplate.getForObject(String.format("%s/cardNumber/%s", cardEntityNumber), CardEntity.class);

        return cardEntity;
    }

    public CardEntity findByCardNumberAndPinCode(Long cardEntityNumber, Short cardEntityPinCode) {
        CardEntity cardEntity = restTemplate.getForObject(String.format("%s/cardNumberAndPinCode/%s/%s", cardEntityNumber, cardEntityPinCode), CardEntity.class);

        return cardEntity;
    }

    public CardEntity findByCardNumberAndFingerprint(Long cardEntityNumber, String cardEntityFingerprint) {
        CardEntity cardEntity = restTemplate.getForObject(String.format("%s/cardNumberAndFingerprint/%s/%s", cardEntityNumber, cardEntityFingerprint), CardEntity.class);

        return cardEntity;
    }

    public AccountEntity findBalance(String cardNumber) {
        AccountEntity accountEntity = restTemplate.getForObject(String.format("%s/balance/%s", cardNumber), AccountEntity.class);

        return accountEntity/*.getBalance()*/;
    }

    public AccountEntity doDeposit(String cardNumber, BigDecimal amount) {
        AccountEntity accountEntity = restTemplate.getForObject(String.format("%s/deposit/%s/%s", cardNumber, amount), AccountEntity.class);

        return accountEntity;
    }

    public AccountEntity doWithdrawal(String cardNumber, BigDecimal amount) {
        AccountEntity accountEntity = restTemplate.getForObject(String.format("%s/withdrawal/%s/%s", cardNumber, amount), AccountEntity.class);

        return accountEntity;
    }
}
