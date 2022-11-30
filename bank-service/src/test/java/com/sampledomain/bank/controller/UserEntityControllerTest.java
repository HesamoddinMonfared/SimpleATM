package com.sampledomain.bank.controller;


import com.sampledomain.bank.BankServiceApplication;
import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserEntityController.class)
@WithMockUser
public class UserEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityService userEntityService;


    @Test
    public void UserEntityController_whenFindUserByNationalCode_thenOK() throws Exception {
        UserEntity mockUserEntity = new UserEntity(1L, "2", "Ali", "ahmadi", "0912", "12345", null);

        Mockito.when(userEntityService.findUserEntityByNationalCode("2")).thenReturn(Optional.of(mockUserEntity));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
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
        Mockito.when(userEntityService.saveUserEntity(mockUserEntity)).thenReturn(mockUserEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/V1/banks/users/")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }
    

}
