package com.example.recommendation.controller;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.UserAlreadyExistsException;
import com.example.recommendation.model.UserDetails;
import com.example.recommendation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    @Autowired
    @Qualifier("userService")
    IUserService userService;


    @PostMapping(value = "/addUser")
    public ResponseEntity<UserDetails> addUser(@RequestBody @Valid UserDetails userDetails) throws UserAlreadyExistsException, MethodNotAllowedException {
        return new ResponseEntity<>(userService.addUser(userDetails), HttpStatus.CREATED);
    }


}
