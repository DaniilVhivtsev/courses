package com.fitness.courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RefreshTokenDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.auth.service.AuthService;

import jakarta.mail.MessagingException;

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
    public ResponseEntity<JwtResponse> authentication(@RequestBody LoginRequest loginRequest) throws MessagingException
    {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/registration", consumes = "application/json")
    public ResponseEntity<Void> registration(@RequestBody RegistrationUserInfoDto registrationUserInfoDto)
            throws MessagingException
    {
        authService.registration(registrationUserInfoDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/refreshtoken", consumes = "application/json")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto)
    {
        return new ResponseEntity<>(authService.refresh(refreshTokenDto), HttpStatus.OK);
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<?> verificationEmail(@RequestParam String code, @RequestParam String secondeCode)
    {
        authService.confirmEmailByCode(secondeCode, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
