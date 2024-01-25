package com.fitness.courses.http.student.service.student;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.user.model.User;

public interface CrudStudentService
{
    StudentEntity save(StudentEntity entity);

    StudentEntity update(@NotNull StudentEntity entity);

    Optional<StudentEntity> findById(Long id);

    StudentEntity findByIdOrThrow(Long id) throws NotFoundException;

    List<StudentEntity> findAllByCourse(CourseEntity course);

    int countByCourse(CourseEntity entity);

    boolean studentWithUserAndCourseExist(User user, CourseEntity course);

    List<StudentEntity> getByUserOrderById(User user);

    Optional<StudentEntity> getByUserAndCourse(User user, CourseEntity course);
}
