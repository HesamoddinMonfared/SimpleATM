package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("/")
    public UserEntity saveUser(@RequestBody UserEntity userEntity){
        return userEntityService.saveUserEntity(userEntity);
    }

    @GetMapping("/{id}")
    public UserEntity findUserById(@PathVariable("id") Long userEntityId){
        return userEntityService.findUserEntityById(userEntityId);
    }
}
