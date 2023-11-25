package com.fitness.courses.http.authorization;

import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_EMAIL;
import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_FIRST_NAME;
import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_LAST_NAME;
import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_PASSWORD;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.courses.CoursesApplication;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequestDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.example.DSLExample;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;
import com.fitness.courses.utils.AuthenticateService;
import com.fitness.courses.utils.DSLResponse;

@SpringBootTest(classes = CoursesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorizationControllerTest
{
    private final int port;
    private final DSLAuthorization dslAuthorization;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final AuthenticateService authenticateService;

    @Autowired
    public AuthorizationControllerTest(
            @LocalServerPort int port,
            UserService userService,
            ObjectMapper objectMapper,
            AuthenticateService authenticateService)
    {
        this.port = port;
        this.dslAuthorization = new DSLAuthorization(port, objectMapper);
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authenticateService = authenticateService;
    }

    @BeforeAll
    static void setUp()
    {
    }

    @Test
    void authorization() throws JsonProcessingException, InterruptedException
    {
        JwtResponse jwtResponse = authenticateService.createNewUserAndAuthenticate(dslAuthorization);
        DSLExample dslExample = new DSLExample(port, objectMapper, jwtResponse);
        String exampleStringResponse = dslExample.exampleRequest().getBodyIfStatusOk();
        Assertions.assertEquals("Current user with email " + USER_EMAIL, exampleStringResponse);

        exampleStringResponse = dslExample.exampleRequest().getBodyIfStatusOk();
        Assertions.assertEquals("Current user with email " + USER_EMAIL, exampleStringResponse);

        // Check bad access token
        Thread.sleep(60000);
        DSLResponse<String> badAuthorizationResponse = dslExample.exampleRequest();
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), badAuthorizationResponse.statusCode());

        // Check create new access token by refresh token
        jwtResponse = (JwtResponse) dslAuthorization.refreshToken(jwtResponse.getRefreshToken()).getBodyIfStatusOk();
        dslExample = new DSLExample(port, objectMapper, jwtResponse);
        exampleStringResponse = dslExample.exampleRequest().getBodyIfStatusOk();
        Assertions.assertEquals("Current user with email " + USER_EMAIL, exampleStringResponse);
    }

    @Test
    void registrationTest()
    {
        Assertions.assertTrue(userService.findByEmail(USER_EMAIL).isEmpty());

        RegistrationUserInfoDto newUserInfo = new RegistrationUserInfoDto();
        newUserInfo.setEmail(USER_EMAIL);
        newUserInfo.setFirstName(USER_FIRST_NAME);
        newUserInfo.setLastName(USER_LAST_NAME);
        newUserInfo.setPassword(USER_PASSWORD);

        dslAuthorization.registration(newUserInfo).getBodyIfStatusOk();

        Optional<User> newUserFromDBOptional = userService.findByEmail(USER_EMAIL);
        Assertions.assertTrue(newUserFromDBOptional.isPresent());

        DSLResponse<?> responseVoid = dslAuthorization.registration(newUserInfo);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseVoid.statusCode());
        Assertions.assertEquals("User with email is already exist",
                Objects.requireNonNull(responseVoid.responseEntity().getBody()).toString());
    }

    @Test
    void authenticationTest() throws JsonProcessingException
    {
        RegistrationUserInfoDto newUserInfo = new RegistrationUserInfoDto();
        newUserInfo.setEmail(USER_EMAIL);
        newUserInfo.setFirstName(USER_FIRST_NAME);
        newUserInfo.setLastName(USER_LAST_NAME);
        newUserInfo.setPassword(USER_PASSWORD);

        dslAuthorization.registration(newUserInfo).getBodyIfStatusOk();

        LoginRequestDto badLoginCredentials = new LoginRequestDto();
        badLoginCredentials.setLogin(USER_EMAIL);
        badLoginCredentials.setPassword(USER_PASSWORD + "1");

        DSLResponse<?> responseAuthentication = dslAuthorization.authenticate(badLoginCredentials);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseAuthentication.statusCode());
        Assertions.assertEquals("Can't find user by login or password is incorrect",
                Objects.requireNonNull(responseAuthentication.responseEntity().getBody()).toString());

        badLoginCredentials.setLogin("1");
        responseAuthentication = dslAuthorization.authenticate(badLoginCredentials);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseAuthentication.statusCode());
        Assertions.assertEquals("User with email doesn't exist",
                Objects.requireNonNull(responseAuthentication.responseEntity().getBody()).toString());

        LoginRequestDto correctLoginCredentials = new LoginRequestDto();
        correctLoginCredentials.setLogin(USER_EMAIL);
        correctLoginCredentials.setPassword(USER_PASSWORD);
        responseAuthentication = dslAuthorization.authenticate(correctLoginCredentials);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseAuthentication.statusCode());
        Assertions.assertEquals("User email isn't confirmed",
                Objects.requireNonNull(responseAuthentication.responseEntity().getBody()).toString());

        Optional<User> newUserFromDBOptional = userService.findByEmail(USER_EMAIL);
        Assertions.assertTrue(newUserFromDBOptional.isPresent());

        User newUserFromDB = newUserFromDBOptional.get();
        newUserFromDB.setConfirmed(true);
        userService.save(newUserFromDB);

        JwtResponse jwtResponse = (JwtResponse)dslAuthorization.authenticate(correctLoginCredentials)
                .getBodyIfStatusOk();
        Assertions.assertNotNull(jwtResponse);
        Assertions.assertNotNull(jwtResponse.getAccessToken());
        Assertions.assertNotNull(jwtResponse.getRefreshToken());

        DSLExample dslExample = new DSLExample(port, objectMapper, jwtResponse);
        String exampleStringResponse = dslExample.exampleRequest().getBodyIfStatusOk();
        Assertions.assertEquals("Current user with email " + USER_EMAIL, exampleStringResponse);
    }

    @AfterEach
    void afterEach()
    {
    }

    @AfterAll
    static void afterAll()
    {
    }
}
