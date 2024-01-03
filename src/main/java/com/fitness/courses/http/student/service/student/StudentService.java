package com.fitness.courses.http.student.service.student;

import java.util.List;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.user.model.User;

public interface StudentService
{
    List<StudentEntity> getCourseStudents(CourseEntity course);

    int countByCourse(CourseEntity course);

    boolean studentWithUserAndCourseExist(User user, CourseEntity course);
}
