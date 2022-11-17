package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.CardEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/V1")
public class CardEntityController {

    @Autowired
    private CardEntityService cardEntityService;

    @GetMapping("/cards/{cardEntityId}")
    public ResponseEntity<CardEntity> findCardEntityById(@PathVariable Long cardEntityId) throws ResourceNotFoundException {
        CardEntity cardEntity = cardEntityService.findCardEntityById(cardEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Card with id: " + cardEntityId));

        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

}
