package com.fitness.courses.http.authorization;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.utils.DSLResponse;

public class DSLAuthorization
{
    private static final String HOST = "http://localhost:";

    private final String baseUrl;
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper objectMapper;

    public DSLAuthorization(int port, ObjectMapper objectMapper)
    {
        this.baseUrl = HOST + port;
        this.objectMapper = objectMapper;
    }

    public DSLResponse<?> registration(RegistrationUserInfoDto user)
    {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");

        final HttpEntity<RegistrationUserInfoDto> entity = new HttpEntity<>(user, httpHeaders);
        final String url = baseUrl + "/auth/registration";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().value() == HttpStatus.OK.value())
        {
            return new DSLResponse<>(null, response);
        }

        return new DSLResponse<>(response.getBody(), response);
    }

    public DSLResponse<?> authenticate(LoginRequest loginRequest) throws JsonProcessingException
    {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");

        final HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, httpHeaders);
        final String url = baseUrl + "/auth";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().value() == HttpStatus.OK.value())
        {
            final JwtResponse value = objectMapper.readValue(response.getBody(), JwtResponse.class);
            return new DSLResponse<>(value, new ResponseEntity<>(value, response.getStatusCode()));
        }

        return new DSLResponse<>(response.getBody(), response);
    }

    public DSLResponse<?> refreshToken(String refreshToken) throws JsonProcessingException
    {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");

        final HttpEntity<String> entity = new HttpEntity<>(refreshToken, httpHeaders);
        final String url = baseUrl + "/auth/refreshtoken";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().value() == HttpStatus.OK.value())
        {
            final JwtResponse value = objectMapper.readValue(response.getBody(), JwtResponse.class);
            return new DSLResponse<>(value, new ResponseEntity<>(value, response.getStatusCode()));
        }

        return new DSLResponse<>(response.getBody(), response);
    }
}
