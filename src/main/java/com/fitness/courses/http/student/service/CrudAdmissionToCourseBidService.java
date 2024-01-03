package com.fitness.courses.http.student.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.user.model.User;

public interface CrudAdmissionToCourseBidService
{
    AdmissionToCourseBidEntity save(@NotNull AdmissionToCourseBidEntity entity);

    Optional<AdmissionToCourseBidEntity> findById(@NotNull Long id);

    AdmissionToCourseBidEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException;

    List<AdmissionToCourseBidEntity> findAllByStudent(@NotNull User user);

    List<AdmissionToCourseBidEntity> findAllByCourse(@NotNull CourseEntity course);
}
