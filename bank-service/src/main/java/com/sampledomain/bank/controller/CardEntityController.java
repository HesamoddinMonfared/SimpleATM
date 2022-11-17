package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.AccountEntity;
import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.AccountEntityService;
import com.sampledomain.bank.service.CardEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/V1")
public class CardEntityController {

    @Autowired
    private CardEntityService cardEntityService;

    @Autowired
    private AccountEntityService accountEntityService;

    @GetMapping("/cards/{cardEntityId}")
    public ResponseEntity<CardEntity> findCardEntityById(@PathVariable Long cardEntityId) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.findCardEntityById(cardEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Card with id: " + cardEntityId));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountEntityId}/cards/")
    public ResponseEntity<CardEntity> createCard(@PathVariable Long accountEntityId,
                                                 @RequestBody CardEntity cardEntity) throws ResourceNotFoundException {
        AccountEntity accountEntity = accountEntityService.findAccountEntityById(accountEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found account with id: " + accountEntityId));
        cardEntity.setAccountEntity(accountEntity);
        cardEntityService.save(cardEntity);
        return new ResponseEntity<>(cardEntity, HttpStatus.CREATED);
    }
}
