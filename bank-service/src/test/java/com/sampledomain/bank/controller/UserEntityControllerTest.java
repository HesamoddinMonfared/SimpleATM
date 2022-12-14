package com.sampledomain.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserEntityController.class)
@WithMockUser
public class UserEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityService userEntityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void UserEntityController_whenFindUserByNationalCode_thenOK() throws Exception {
        UserEntity mockUserEntity = new UserEntity(1L, "2", "Ali", "ahmadi", "0912", "12345", null);

        when(userEntityService.findUserEntityByNationalCode("2")).thenReturn(Optional.of(mockUserEntity));
        RequestBuilder requestBuilder = get(
                "/api/V1/banks/users/findUserEntityByNationalCode/2").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "{\"id\":1,\"nationalCode\":\"2\",\"name\":\"Ali\",\"family\":\"ahmadi\",\"mobile\":\"0912\",\"fingerprint\":\"12345\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void UserEntityController_whenSaveRequest_thenOK() throws Exception {
        String exampleUserJson = "{\"id\":1,\"nationalCode\":\"2\",\"name\":\"Ali\",\"family\":\"ahmadi\",\"mobile\":\"0912\",\"fingerprint\":\"12345\"}";
        UserEntity mockUserEntity = new UserEntity(1L, "2", "Ali", "ahmadi", "0912", "12345", null);
        when(userEntityService.saveUserEntity(mockUserEntity)).thenReturn(mockUserEntity);

        RequestBuilder requestBuilder = post("/api/V1/banks/users/")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    void UserEntityController_whenSaveUser_thenIsCreated() throws Exception {
        UserEntity user = new UserEntity(2l, "207", "zzz", "zzz", "+98902", "fingerzzz", new ArrayList<>());
        mockMvc.perform(post("/api/V1/banks/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void UserEntityController_whenFindUserWithNationalCode_thenReturnOk() throws Exception {
        String nationalCode = "207";
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
    void UserEntityController_whenFindUserWithNationalCode_thenReturnNotFound() throws Exception {
        String nationalCode = "208";

        when(userEntityService.findUserEntityByNationalCode(nationalCode)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/V1/banks/users/findUserEntityByNationalCode/{userEntityNationalCode}", nationalCode))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void UserEntityController_whenFindAllUsers_thenReturnListOfAllUsers() throws Exception {
        List<UserEntity> users = new ArrayList<>(
                Arrays.asList(new UserEntity(1L, "201", "esmaeil1", "chitgar1", "+98911", "fingerprint1", new ArrayList<>()),
                        new UserEntity(2L, "202", "esmaeil2", "chitgar2", "+98912", "fingerprint2", new ArrayList<>())));
        when(userEntityService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/V1/banks/users/findAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andDo(print());
    }
}
