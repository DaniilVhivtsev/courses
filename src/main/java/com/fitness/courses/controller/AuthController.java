package com.fitness.courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RefreshTokenDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.auth.service.AuthService;

@RestController
@RequestMapping(
        path = "/auth",
        produces = "application/json"
)
public class AuthController
{
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> authentication(@RequestBody LoginRequest loginRequest)
    {
        try
        {
            return new ResponseEntity<JwtResponse>(authService.login(loginRequest), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationUserInfoDto registrationUserInfoDto)
    {
        try
        {
            authService.registration(registrationUserInfoDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/refreshtoken", consumes = "application/json")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken)
    {
        try
        {
            return new ResponseEntity<JwtResponse>(authService.refresh(refreshToken), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<?> verificationEmail(@RequestParam String code, @RequestParam String secondeCode)
    {
        try
        {
            authService.confirmEmailByCode(secondeCode, code);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }

    }
}
