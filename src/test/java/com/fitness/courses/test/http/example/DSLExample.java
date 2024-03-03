package com.fitness.courses.test.http.example;

import static com.fitness.courses.test.HttpConstants.HOST;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.test.utils.DSLResponse;

public class DSLExample
{
    private final String baseUrl;
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper objectMapper;
    private final JwtResponse jwtResponse;

    public DSLExample(int port, ObjectMapper objectMapper, JwtResponse jwtResponse)
    {
        this.baseUrl = HOST + port;
        this.objectMapper = objectMapper;
        this.jwtResponse = jwtResponse;
    }

    public DSLResponse<String> exampleRequest()
    {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        httpHeaders.add("Authorization", String.format("%s %s", JwtResponse.TYPE, jwtResponse.getAccessToken()));

        final HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        final String url = baseUrl + "/example/authenticated/request";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return new DSLResponse<>(response.getBody(), response);
    }
}
