package com.sampledomain.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
//@WebMvcTest(value = CardEntityController.class)
@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class CardEntityControllerTest {
    @MockBean
    private CardEntityController cardEntityController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    void shouldUpdateTutorial() {
//        String cardNumber = "504171080286363";
//        BigDecimal amount = BigDecimal.valueOf(200);
//        CardEntity card = new CardEntity(1L, cardNumber, (short) 1576, 0, false, PasswordType.Pin, new AccountEntity());
//        AccountEntity account = card.getAccountEntity();
//        UserEntity user = account.getUserEntity();
//
//        PrintOutput printOutput = new PrintOutput(LocalDateTime.now(), user.fullName(), card.getCardNumber(), account.getAccountNumber(), account.getBalance().add(amount));
//
//        when(cardEntityService.findCardByCardNumber(cardNumber)).thenReturn(Optional.of(card));
//        when(cardEntityService.doDeposit(cardNumber, amount)).thenReturn(Optional.of(printOutput));
//
//        mockMvc.perform(post("/api/V1/banks/cards/deposit/{cardEntityNumber}/{amount}", cardNumber, amount).contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(printOutput)))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.title").value(updatedtutorial.getTitle()))
////                .andExpect(jsonPath("$.description").value(updatedtutorial.getDescription()))
////                .andExpect(jsonPath("$.published").value(updatedtutorial.isPublished()))
//                .andDo(print());
    }
}