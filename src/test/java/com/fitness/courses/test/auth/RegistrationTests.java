package com.fitness.courses.test.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fitness.courses.CoursesApplication;

@SpringBootTest(classes = CoursesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegistrationTests
{
    /*@LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private final MockMvc mockMvc;

    @Autowired
    public RegistrationTests(MockMvc mockMvc)
    {
        this.mockMvc = mockMvc;
    }*/

    @Test
    public void successRegisterUser() throws Exception
    {
//        ResultActions registrationResult = mockMvc.perform(post("/auth/registration"));
//        Assertions.assertEquals(registrationResult.ex);
    }
}
