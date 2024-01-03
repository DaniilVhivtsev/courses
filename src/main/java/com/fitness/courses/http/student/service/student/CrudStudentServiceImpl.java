package com.fitness.courses.http.student.service.student;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.student.repository.StudentEntityRepository;
import com.fitness.courses.http.user.model.User;

@Service
public class CrudStudentServiceImpl implements CrudStudentService
{
    private final StudentEntityRepository repository;

    public CrudStudentServiceImpl(StudentEntityRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional
    public StudentEntity save(StudentEntity entity)
    {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public StudentEntity update(@NotNull StudentEntity entity)
    {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentEntity> findById(Long id)
    {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentEntity findByIdOrThrow(Long id) throws NotFoundException
    {
        return this.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(("Can't find student entity by id %s").formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> findAllByCourse(CourseEntity course)
    {
        return repository.findAllByCourseId(course.getId());
    }


    @Override
    @Transactional(readOnly = true)
    public int countByCourse(CourseEntity course)
    {
        return repository.countByCourseId(course.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean studentWithUserAndCourseExist(User user, CourseEntity course)
    {
        return repository.findFirstByUserIdAndCourseId(user.getId(), course.getId()).isPresent();
    }
}
