package com.fitness.courses.http.greeting.service;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingUpdateDto;
import com.fitness.courses.http.greeting.model.dto.GreetingContent;
import com.fitness.courses.http.greeting.model.dto.StudentGreetingContent;

public interface RestGreetingService
{
    void update(Long courseId, GreetingUpdateDto greetingUpdateDto);

    GreetingContent getGreetingContent(@NotNull Long courseId);

    StudentGreetingContent getStudentGreetingContent(@NotNull Long courseId);
}
