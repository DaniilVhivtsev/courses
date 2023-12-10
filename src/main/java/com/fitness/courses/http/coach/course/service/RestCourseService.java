package com.fitness.courses.http.coach.course.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;

public interface RestCourseService
{
    void createCourse(@NonNull NewCourseDto newCourseDto);

    List<ListCourseInfoDto> getAuthorCourses();

    CourseAuthorGeneralInfoDto getAuthorCourseGeneralInfo(Long courseId);

    CourseAuthorGeneralInfoDto editAuthCourseGeneralInfo(Long courseId,
            EditCourseAuthorGeneralInfo editCourseAuthorGeneralInfo);

    List<UserGeneralInfoDto> getAuthorCourseStudents(Long courseId);

    CourseAuthorContentInfo getAuthorCourseContent(@NotNull Long courseId);

    void addModule(@NotNull Long courseId, @NotNull NewCourseAuthorModuleDto newModuleDto);

    void editModule(@NotNull Long courseId, @NotNull Long moduleId,
            @NotNull UpdateCourseAuthorModuleDto updateModuleDto);

    void addLesson(@NotNull Long courseId, @NotNull Long moduleId, @NotNull NewCourseAuthorLessonDto newLessonDto);
}
