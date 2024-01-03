package com.fitness.courses.http.student.service.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.user.model.User;

@Service
public class StudentServiceImpl implements StudentService
{
    private final CrudStudentService crudStudentService;

    @Autowired
    public StudentServiceImpl(CrudStudentService crudStudentService)
    {
        this.crudStudentService = crudStudentService;
    }

    @Override
    public List<StudentEntity> getCourseStudents(CourseEntity course)
    {
        return crudStudentService.findAllByCourse(course);
    }

    @Override
    public int countByCourse(CourseEntity course)
    {
        return crudStudentService.countByCourse(course);
    }

    @Override
    public boolean studentWithUserAndCourseExist(User user, CourseEntity course)
    {
        return crudStudentService.studentWithUserAndCourseExist(user, course);
    }
}
