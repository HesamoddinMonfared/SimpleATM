package com.sampledomain.bank.service;


import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.repository.CardEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CardEntityService {
    @Autowired
    private CardEntityRepository cardEntityRepository;

    public Optional<CardEntity> findByCardNumber(String cardEntityNumber) {
        return cardEntityRepository.findByCardNumber(cardEntityNumber);
    }
    public Optional<CardEntity> findByCardNumberAndPinCode(String cardEntityNumber, Short cardEntityPinCode) {
        return cardEntityRepository.findByCardNumberAndPinCode(cardEntityNumber, cardEntityPinCode);
    }
    public Optional<CardEntity> findByCardNumberAndFingerprint(String cardEntityNumber, String cardEntityFingerprint) {
        return cardEntityRepository.findByCardNumberAndFingerprint(cardEntityNumber, cardEntityFingerprint);
    }

    public Optional<BigDecimal> findBalance(String cardEntityNumber) {
        return cardEntityRepository.findCardEntityByCardNumber(cardEntityNumber);
    }

    public Optional<CardEntity> doDeposit(String cardEntityNumber, BigDecimal amount) {
        return cardEntityRepository.doDeposit(cardEntityNumber, amount);
    }

    public Optional<CardEntity> doWithdrawal(String cardEntityNumber, BigDecimal amount) {
        return cardEntityRepository.doWithdrawal(cardEntityNumber, amount);
    }
}
