package com.sampledomain.bank.service;

import com.sampledomain.bank.repository.AccountEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEntityService {

    @Autowired
    AccountEntityRepository accountEntityRepository;
}
