package com.fitness.courses.http.coach.course.service;

import java.util.List;

import org.springframework.lang.NonNull;

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
}
