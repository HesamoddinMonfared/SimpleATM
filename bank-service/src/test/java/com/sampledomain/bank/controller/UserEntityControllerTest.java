package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    public void retrieveDetailsForUserEntity() throws Exception {

        /*UserEntity mockUserEntity = new UserEntity(1L, "Ali");

        Mockito.when(userEntityService.findUserEntityById(1L)).thenReturn(mockUserEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{\"id\":1,\"userEntityName\":\"Ali\"}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);*/
    }
}
