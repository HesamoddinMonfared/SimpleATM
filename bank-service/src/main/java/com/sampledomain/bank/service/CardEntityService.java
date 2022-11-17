package com.sampledomain.bank.service;


import com.sampledomain.bank.entity.CardEntity;
import com.sampledomain.bank.repository.CardEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardEntityService {

    @Autowired
    private CardEntityRepository cardEntityRepository;

    public Optional<CardEntity> findCardEntityById(Long cardEntityId) {
        return cardEntityRepository.findCardEntityById(cardEntityId);
    }
}
