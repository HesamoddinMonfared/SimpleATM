package com.sampledomain.bank.controller;

import com.sampledomain.bank.entity.UserEntity;
import com.sampledomain.bank.exception.ResourceNotFoundException;
import com.sampledomain.bank.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/V1/banks")
@Slf4j
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("/users")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity userEntity) throws ResourceNotFoundException {
        UserEntity _userEntity = userEntityService.saveUserEntity(userEntity);
        return new ResponseEntity<>(_userEntity, HttpStatus.CREATED);
    }

    @GetMapping("/users/findUserEntityByNationalCode/{userEntityNationalCode}")
    public ResponseEntity<Object> findUserEntityByNationalCode(@PathVariable("userEntityNationalCode") String userEntityNationalCode) throws ResourceNotFoundException {
        var userEntity = userEntityService.findUserEntityByNationalCode(userEntityNationalCode);

        if (userEntity.isEmpty())
            return new ResponseEntity<>("User with specified national-code not exists.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userEntity.get(), HttpStatus.OK);
    }

    @GetMapping("/users/findAllUsers")
    public ResponseEntity<Object> findAllUsers() throws ResourceNotFoundException {
        var allUsers = userEntityService.findAllUsers();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
