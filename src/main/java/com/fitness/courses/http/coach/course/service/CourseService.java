package com.fitness.courses.http.coach.course.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

public interface CourseService
{
    void createCourse(@NonNull CourseEntity newCourseEntity);

    CourseEntity editCourseGeneralInfo(Long courseId, CourseEntity editCourseInfo, MultipartFile logo);

    Optional<CourseEntity> getCourseOptional(@NotNull Long id);

    CourseEntity getCourseOrThrow(@NotNull Long id);

    List<CourseEntity> getAllCoursesWhereCurrentUserIsAuthor();
}
