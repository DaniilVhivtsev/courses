package com.fitness.courses.test.http.course.coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.courses.CoursesApplication;
import com.fitness.courses.test.http.authorization.DSLAuthorization;
import com.fitness.courses.http.user.service.UserService;
import com.fitness.courses.test.utils.AuthenticateService;

@SpringBootTest(classes = CoursesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class CoachCourseControllerTest
{
    private final DSLAuthorization dslAuthorization;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final AuthenticateService authenticateService;

    @Autowired
    public CoachCourseControllerTest(
            @LocalServerPort int port,
            UserService userService,
            ObjectMapper objectMapper,
            AuthenticateService authenticateService)
    {
        this.dslAuthorization = new DSLAuthorization(port, objectMapper);
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authenticateService = authenticateService;
    }
}
