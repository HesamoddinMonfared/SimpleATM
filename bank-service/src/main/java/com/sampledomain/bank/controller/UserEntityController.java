package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/V1/banks")
@Slf4j
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("/users/")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity userEntity){
        UserEntity _userEntity = userEntityService.saveUserEntity(userEntity);
        return new ResponseEntity<>(_userEntity, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userEntityId}")
    public ResponseEntity<UserEntity> findUserEntityById(@PathVariable("userEntityId") Long userEntityId) throws ResourceNotFoundException {
        UserEntity userEntity = userEntityService.findUserEntityById(userEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found UserEntity with id: " + userEntityId));

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
