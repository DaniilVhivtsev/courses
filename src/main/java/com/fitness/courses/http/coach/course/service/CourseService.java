package com.fitness.courses.http.coach.course.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.model.info.CourseEntityWithStudentsCount;

public interface CourseService
{
    void createCourse(@NonNull CourseEntity newCourseEntity);

    void delete(@NonNull Long courseId);

    CourseEntity editCourseGeneralInfo(Long courseId, CourseEntity editCourseInfo, MultipartFile logo);

    Optional<CourseEntity> getCourseOptional(@NotNull Long id);

    CourseEntity getCourseOrThrow(@NotNull Long id);

    List<CourseEntity> getAllCoursesWhereCurrentUserIsAuthor();

    List<CourseEntity> getNewCourses(@NotNull Integer offset, @NotNull Integer limit);

    List<CourseEntityWithStudentsCount> getPopularCourses(@NotNull Integer offset, @NotNull Integer limit);

    List<CourseEntity> findAllByKeyword(@NotNull String keyword, @NotNull Integer offset,
            @NotNull Integer limit);
}
