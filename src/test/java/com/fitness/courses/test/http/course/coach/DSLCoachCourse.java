package com.fitness.courses.test.http.course.coach;

import static com.fitness.courses.test.HttpConstants.HOST;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.test.utils.DSLResponse;

public class DSLCoachCourse
{
    private final String baseUrl;
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final JwtResponse jwtResponse;

    public DSLCoachCourse(int port, JwtResponse jwtResponse)
    {
        this.baseUrl = HOST + port;
        this.jwtResponse = jwtResponse;
    }

    public @NotNull DSLResponse<Long> createCourse(@NotNull NewCourseDto newCourseDto)
    {
        final String url = baseUrl + "/coach/course/create";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("%s %s", JwtResponse.TYPE, jwtResponse.getAccessToken()));

        HttpEntity<NewCourseDto> requestEntity = new HttpEntity<>(newCourseDto, headers);
        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Long.class);
        return new DSLResponse<>(response.getBody(), response);
    }

    public @NotNull DSLResponse<Void> deleteCourse(@NotNull Long courseId)
    {
        final String url = baseUrl + "/course/author/" + courseId;
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("%s %s", JwtResponse.TYPE, jwtResponse.getAccessToken()));

        HttpEntity<NewCourseDto> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
        return new DSLResponse<>(response.getBody(), response);
    }

    public @NotNull DSLResponse<List<ListCourseInfoDto>> getAllAuthorCourses()
    {
        final String url = baseUrl + "/course/author/get/all";
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("%s %s", JwtResponse.TYPE, jwtResponse.getAccessToken()));

        HttpEntity<NewCourseDto> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<ListCourseInfoDto>> response =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
        return new DSLResponse<>(response.getBody(), response);
    }
}
