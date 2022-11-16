package com.sampledomain.bank.service;


import com.sampledomain.bank.repository.CardEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardEntityService {

    @Autowired
    private CardEntityRepository cardEntityRepository;
}
