package com.sampledomain.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = UserEntityController.class)
@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
public class UserEntityControllerTest {
    @MockBean
    private UserEntityService userEntityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void saveUser() throws Exception {
        UserEntity user = new UserEntity(1l, "207", "zzz", "zzz", "+98902", "fingerzzz", new ArrayList<>());
        mockMvc.perform(post("/api/V1/banks/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnTutorial() throws Exception {
        String nationalCode = "205";
        UserEntity user = new UserEntity(1l, nationalCode, "esmaeil", "chitgar", "+98911", "fingerchitgar", new ArrayList<>());

        when(userEntityService.findUserEntityByNationalCode(nationalCode)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/api/V1/banks/users/{userEntityNationalCode}", nationalCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nationalCode").value(user.getNationalCode()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.family").value(user.getFamily()))
                .andExpect(jsonPath("$.mobile").value(user.getMobile()))
                .andExpect(jsonPath("$.fingerprint").value(user.getFingerprint()))
                .andDo(print());
    }


    @Test
    void shouldReturnNotFoundTutorial() throws Exception {
        String nationalCode = "208";

        when(userEntityService.findUserEntityByNationalCode(nationalCode)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/V1/banks/users/findUserEntityByNationalCode/{userEntityNationalCode}", nationalCode))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorials() throws Exception {
        List<UserEntity> users = new ArrayList<>(
                Arrays.asList(new UserEntity(1L, "201", "esmaeil1", "chitgar1", "+98911", "fingerprint1", new ArrayList<>()),
                        new UserEntity(2L, "202", "esmaeil2", "chitgar2", "+98912", "fingerprint2", new ArrayList<>())));
        when(userEntityService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/V1/banks/users/findAllUsers"))
                //.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andDo(print());
    }
}
