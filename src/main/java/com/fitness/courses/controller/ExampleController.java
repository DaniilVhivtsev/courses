package com.fitness.courses.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.http.user.model.User;

@RestController
@RequestMapping("/")
public class ExampleController
{
    @PostMapping("/example/authenticated/request")
    public ResponseEntity<?> examplePostMethod(Authentication authentication)
    {
        return new ResponseEntity<>("Current user with email " + authentication.getName(), HttpStatus.OK);
    }
}
