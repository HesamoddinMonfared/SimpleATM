package com.sampledomain.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.repository.CardEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardEntityController.class)
class CardEntityControllerTest {
    @MockBean
    private CardEntityRepository tutorialRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void enterCard() throws Exception {
        Long id =1l;
        UserEntity mockUserEntity = new UserEntity(id, "207", "zzz", "zzz", "+98902", "fingerzzz", new ArrayList<>());
        mockMvc.perform(post("/api/V1/banks/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserEntity)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}