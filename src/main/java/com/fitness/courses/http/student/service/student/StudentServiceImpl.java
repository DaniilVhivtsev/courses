package com.fitness.courses.http.student.service.student;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseService;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.user.model.User;

@Service
public class StudentServiceImpl implements StudentService
{
    private final CrudStudentService crudStudentService;
    private final CourseService courseService;

    @Autowired
    public StudentServiceImpl(
            CrudStudentService crudStudentService,
            CourseService courseService)
    {
        this.crudStudentService = crudStudentService;
        this.courseService = courseService;
    }

    @Override
    public StudentEntity create(@NotNull AdmissionToCourseBidEntity approvedBid)
    {
        final StudentEntity studentEntity = new StudentEntity();
        studentEntity.setCourse(approvedBid.getCourse());
        studentEntity.setApprovedBid(approvedBid);
        studentEntity.setUser(approvedBid.getUser());
        studentEntity.setDoneStageAndSetUuids(new HashSet<>());

        return crudStudentService.save(studentEntity);
    }

    @Override
    public List<StudentEntity> getStudents(CourseEntity course)
    {
        return crudStudentService.findAllByCourse(course);
    }

    @Override
    public int countByCourse(CourseEntity course)
    {
        return crudStudentService.countByCourse(course);
    }

    @Override
    public List<CourseEntity> getUserCoursesOrderById(User user)
    {
        return crudStudentService.getByUserOrderById(user).stream()
                .map(StudentEntity::getCourse)
                .toList();
    }

    @Override
    public boolean studentWithUserAndCourseExist(User user, CourseEntity course)
    {
        return crudStudentService.studentWithUserAndCourseExist(user, course);
    }

    @Override
    public Optional<StudentEntity> getByUserAndCourse(@NotNull User user, @NotNull CourseEntity course)
    {
        return crudStudentService.getByUserAndCourse(user, course);
    }

    @Override
    public StudentEntity getByUserAndCourseOrThrow(@NotNull User user, @NotNull CourseEntity course)
    {
        return crudStudentService.getByUserAndCourse(user, course)
                .orElseThrow(() -> new NotFoundException("Can't find student by user and course"));
    }

    @Override
    public Double getCoursePercentagePassed(User user, CourseEntity course)
    {
        final StudentEntity student = crudStudentService.getByUserAndCourse(user, course).orElseThrow();
        Set<String> courseStagesUuids = courseService.getCourseStagesUuids(course);
        final int courseStagesUuidsCount = courseStagesUuids.size();

        courseStagesUuids.removeAll(student.getDoneStageAndSetUuids());
        final int courseDoneStagesUuidsCount = courseStagesUuidsCount - courseStagesUuids.size();

        double coursePercentagePassed = (double)courseDoneStagesUuidsCount * 100 / courseStagesUuidsCount;
        return Double.valueOf(String.format("%.2f", coursePercentagePassed).replace(",", "."));
    }

    @Override
    public void update(StudentEntity student)
    {
        crudStudentService.update(student);
    }
}
