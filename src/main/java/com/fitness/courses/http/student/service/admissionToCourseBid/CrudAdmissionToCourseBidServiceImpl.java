package com.fitness.courses.http.student.service.admissionToCourseBid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.student.repository.AdmissionToCourseBidRepository;
import com.fitness.courses.http.user.model.User;

@Service
public class CrudAdmissionToCourseBidServiceImpl implements CrudAdmissionToCourseBidService
{
    private final AdmissionToCourseBidRepository repository;

    @Autowired
    public CrudAdmissionToCourseBidServiceImpl(
            AdmissionToCourseBidRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional
    public AdmissionToCourseBidEntity save(AdmissionToCourseBidEntity entity)
    {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdmissionToCourseBidEntity> findById(Long id)
    {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AdmissionToCourseBidEntity findByIdOrThrow(Long id) throws NotFoundException
    {
        return this.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(("Can't find admission to the course bid by id %s").formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionToCourseBidEntity> findAllByStudent(User user)
    {
        return repository.findAllByUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionToCourseBidEntity> findAllByCourse(CourseEntity course)
    {
        return repository.findAllByCourseId(course.getId());
    }
}
