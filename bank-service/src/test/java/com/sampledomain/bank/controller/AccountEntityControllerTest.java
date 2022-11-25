package com.sampledomain.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampledomain.bank.service.AccountEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = AccountEntityController.class)
@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class AccountEntityControllerTest {
    @MockBean
    private AccountEntityService accountEntityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAccounts() {
    }
}