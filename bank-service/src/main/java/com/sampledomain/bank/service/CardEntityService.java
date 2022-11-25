package com.sampledomain.bank.service;

import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.entity.PasswordType;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.helper.PrintOutput;
import com.sampledomain.bank.repository.CardEntityRepository;
import com.sampledomain.bank.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CardEntityService {
    @Autowired
    private CardEntityRepository cardEntityRepository;


    @Autowired
    JwtUtils jwtUtils;

    public String verifyEnteredCardByCardNumber(String cardEntityNumber) {
        try {
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
        } catch (Exception e) {
            return "message in CardEntityService::verifyEnteredCardByCardNumber: " + e.getMessage();
        }
    }

    public String verifyExitedCardByCardNumber(String cardEntityNumber) {
        try {
            var cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber);
            if (cardEntity.isEmpty()) {
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
        } catch (Exception e) {
            return "message in AccountEntityService::verifyExitedCardByCardNumber: " + e.getMessage();
        }
    }

    public Optional<CardEntity> findCardByCardNumber(String cardEntityNumber) throws ResourceNotFoundException {
        try {
            return cardEntityRepository.findByCardNumber(cardEntityNumber);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::findCardByCardNumber: " + e.getMessage());
        }
    }

    public String loginByCardNumberAndPinCode(String cardEntityNumber, Short cardEntityPinCode) throws ResourceNotFoundException {
        try {
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

            String jwt = jwtUtils.generateJwtToken();

            return jwt; //Login successfully
        } catch (Exception e) {
            return "message in AccountEntityService::loginByCardNumberAndPinCode: " + e.getMessage();
        }
    }

    public String loginByCardNumberAndFingerprint(String cardEntityNumber, String cardEntityFingerprint) throws ResourceNotFoundException {
        try {
            var cardByCardNumber = findCardByCardNumber(cardEntityNumber);

            if (cardByCardNumber.isEmpty()) {
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

            String jwt = jwtUtils.generateJwtToken();

            return jwt; //Login successfully
        } catch (Exception e) {
            return "message in AccountEntityService::loginByCardNumberAndFingerprint: " + e.getMessage();
        }
    }

    public Optional<PrintOutput> findBalance(String cardEntityNumber) throws ResourceNotFoundException {
        try {
            Optional<CardEntity> cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber);

            if (cardEntity.isEmpty()) {
                throw new ResourceNotFoundException("");
            }

            if (cardEntity.get().isLocked()) {
                throw new ResourceNotFoundException("");
            }

            return print(cardEntity);
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::findBalance: " + e.getMessage());
        }
    }

    public Optional<PrintOutput> doDeposit(String cardNumber, BigDecimal amount) throws ResourceNotFoundException {
        try {
            CardEntity card = cardEntityRepository.findByCardNumber(cardNumber).get();

            var balance = card.getAccountEntity().getBalance();
            card.getAccountEntity().setBalance(balance.add(amount));
            card = cardEntityRepository.save(card);

            return print(Optional.of(card));
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::doDeposit: " + e.getMessage());
        }
    }

    public Optional<PrintOutput> doWithdrawal(String cardEntityNumber, BigDecimal amount) throws ResourceNotFoundException {
        try {
            CardEntity cardEntity = cardEntityRepository.findByCardNumber(cardEntityNumber).get();

            var balance = cardEntity.getAccountEntity().getBalance();
            if (balance.compareTo(amount) >= 0) {
                cardEntity.getAccountEntity().setBalance(balance.min(amount));
                cardEntity = cardEntityRepository.save(cardEntity);
            }

            return print(Optional.of(cardEntity));
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::doWithdrawal: " + e.getMessage());
        }
    }

    public void save(CardEntity card) throws ResourceNotFoundException {
        try {
            if (card != null) {
                cardEntityRepository.save(card);
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::save: " + e.getMessage());
        }
    }

    public Optional<PrintOutput> print(Optional<CardEntity> card) throws ResourceNotFoundException {
        try {
            if (card.isEmpty()) {
                throw new RuntimeException("Card is not valid.");
            }

            var account = card.get().getAccountEntity();
            var user = account.getUserEntity();

            return Optional.of(new PrintOutput(LocalDateTime.now(), user.fullName(), card.get().getCardNumber(), account.getAccountNumber(), account.getBalance()));
        } catch (Exception e) {
            throw new ResourceNotFoundException("message in AccountEntityService::print: " + e.getMessage());
        }
    }
}
