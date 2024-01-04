package com.fitness.courses.http.student.service.student;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.user.model.User;

public interface StudentService
{
    StudentEntity create(@NotNull AdmissionToCourseBidEntity approvedBid);

    List<StudentEntity> getStudents(CourseEntity course);

    int countByCourse(CourseEntity course);

    List<CourseEntity> getUserCoursesOrderById(@NotNull User user);

    boolean studentWithUserAndCourseExist(User user, CourseEntity course);

    Optional<StudentEntity> getByUserAndCourse(@NotNull User user, @NotNull CourseEntity course);

    StudentEntity getByUserAndCourseOrThrow(@NotNull User user, @NotNull CourseEntity course);

    Double getCoursePercentagePassed(User user, CourseEntity course);

    void update(@NotNull StudentEntity student);
}
