package com.sampledomain.bank.service;


import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.helper.PasswordType;
import com.sampledomain.bank.helper.PrintOutput;
import com.sampledomain.bank.repository.CardEntityRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CardEntityService {
    @Autowired
    private CardEntityRepository cardEntityRepository;

    public String verifyEnteredCardByCardNumber(String cardEntityNumber) {
        var cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber);
        if (cardEntity.isPresent()) {
            if (cardEntity.get().getIsInAtm()) {
                return "Card is still in use.";
            }

            cardEntity.get().setIsInAtm(true);

            cardEntityRepository.save(cardEntity.get());

            if (cardEntity.get().getNumOfFailedTimes() < 3) {
                return String.format("Please enter your %s", cardEntity.get().getPasswordType().name());
            }

            return "Card is locked. Please contact admin.";
        }

        return "Card not valid. exiting from atm.";
    }

    public String verifyExitedCardByCardNumber(String cardEntityNumber) {
        var cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber);
        if (!cardEntity.isPresent()) {
            return "Card not exist.";
        }

        if (cardEntity.get().isLocked()) {
            return "Card is locked. Please contact admin.";
        }

        if (!cardEntity.get().getIsInAtm()) {
            return "Card is not in Atm.";
        }

        cardEntity.get().setIsInAtm(false);
        cardEntityRepository.save(cardEntity.get());
        return "Exiting card successfully.";
    }

    public Optional<CardEntity> findCardByCardNumber(String cardEntityNumber) {
        return cardEntityRepository.findByCardNumber(cardEntityNumber);
    }

    public String loginByCardNumberAndPinCode(String cardEntityNumber, Short cardEntityPinCode) {
        var cardByCardNumber = findCardByCardNumber(cardEntityNumber);

        if (!cardByCardNumber.isPresent()) {
            return "Card not exist.";
        }

        if (!cardByCardNumber.get().getIsInAtm()) {
            return "Card is not in Atm.";
        }

        if (cardByCardNumber.get().isLocked()) {
            return "Card is locked. Please contact admin.";
        }

        if (cardByCardNumber.get().getPasswordType().equals(PasswordType.Fingerprint)) {
            cardByCardNumber.get().setNumOfFailedTimes(cardByCardNumber.get().getNumOfFailedTimes() + 1);
            cardEntityRepository.save(cardByCardNumber.get());
            return "You must enter your fingerprint.";
        }

        if (!cardByCardNumber.get().getPinCode().equals(cardEntityPinCode)) {
            cardByCardNumber.get().setNumOfFailedTimes(cardByCardNumber.get().getNumOfFailedTimes() + 1);
            cardEntityRepository.save(cardByCardNumber.get());
            return "Please enter correct PIN code.";
        }

        cardByCardNumber.get().setNumOfFailedTimes(0);
        cardEntityRepository.save(cardByCardNumber.get());
        return "Login successfully. Please proceed.";
    }

    public String loginByCardNumberAndFingerprint(String cardEntityNumber, String cardEntityFingerprint) {
        var cardByCardNumber = findCardByCardNumber(cardEntityNumber);

        if (!cardByCardNumber.isPresent()) {
            return "Card not exist.";
        }

        if (!cardByCardNumber.get().getIsInAtm()) {
            return "Card is not in Atm.";
        }

        if (cardByCardNumber.get().isLocked()) {
            return "Card is locked. Please contact admin.";
        }

        if (cardByCardNumber.get().getPasswordType().equals(PasswordType.Pin)) {
            cardByCardNumber.get().setNumOfFailedTimes(cardByCardNumber.get().getNumOfFailedTimes() + 1);
            cardEntityRepository.save(cardByCardNumber.get());
            return "You must enter your PIN code.";
        }

        if (!cardByCardNumber.get().getAccountEntity().getUserEntity().getFingerprint().equals(cardEntityFingerprint)) {
            cardByCardNumber.get().setNumOfFailedTimes(cardByCardNumber.get().getNumOfFailedTimes() + 1);
            cardEntityRepository.save(cardByCardNumber.get());
            return "Please enter correct fingerprint.";
        }

        cardByCardNumber.get().setNumOfFailedTimes(0);
        cardEntityRepository.save(cardByCardNumber.get());
        return "Login successfully. Please proceed.";
    }

    public Optional<PrintOutput> findBalance(String cardEntityNumber) throws ResourceNotFoundException {
        Optional<CardEntity> cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber);

        if (!cardEntity.isPresent()) {
            throw new ResourceNotFoundException("");
        }

        if (cardEntity.get().isLocked())
        {
            throw new ResourceNotFoundException("");
        }

        return print(cardEntity);
    }

    public Optional<PrintOutput> doDeposit(String cardNumber, BigDecimal amount) {
        CardEntity card = cardEntityRepository.findByCardNumber(cardNumber).get();

        var balance = card.getAccountEntity().getBalance();
        card.getAccountEntity().setBalance(balance.add(amount));
        card = cardEntityRepository.save(card);

        return print(Optional.of(card));
    }

    public Optional<PrintOutput> doWithdrawal(String cardEntityNumber, BigDecimal amount) {
        CardEntity cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber).get();

        var balance = cardEntity.getAccountEntity().getBalance();
        if (balance.compareTo(amount) >= 0) {
            cardEntity.getAccountEntity().setBalance(balance.min(amount));
            cardEntity = cardEntityRepository.save(cardEntity);
        }

        return print(Optional.of(cardEntity));
    }

    public void save(CardEntity card) {
        if (card != null) {
            cardEntityRepository.save(card);
        }
    }

    public Optional<PrintOutput> print(Optional<CardEntity> card) {
        if (!card.isPresent()) {
            throw new RuntimeException("Card is not valid.");
        }

        var account = card.get().getAccountEntity();
        var user = account.getUserEntity();

        return Optional.of(new PrintOutput(LocalDateTime.now(), user.fullName(), card.get().getCardNumber(), account.getAccountNumber(), account.getBalance()));
    }
}
