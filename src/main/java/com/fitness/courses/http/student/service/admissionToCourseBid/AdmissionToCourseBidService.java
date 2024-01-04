package com.fitness.courses.http.student.service.admissionToCourseBid;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.user.model.User;

public interface AdmissionToCourseBidService
{
    AdmissionToCourseBidEntity create(@NotNull User user, @NotNull CourseEntity course);
}
