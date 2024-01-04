package com.fitness.courses.http.student.service.admissionToCourseBid;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.user.model.User;

@Service
public class AdmissionToCourseBidServiceImpl implements AdmissionToCourseBidService
{
    private final CrudAdmissionToCourseBidService crudAdmissionToCourseBidService;

    @Autowired
    public AdmissionToCourseBidServiceImpl(
            CrudAdmissionToCourseBidService crudAdmissionToCourseBidService)
    {
        this.crudAdmissionToCourseBidService = crudAdmissionToCourseBidService;
    }

    @Override
    public AdmissionToCourseBidEntity create(@NotNull User user, @NotNull CourseEntity course)
    {
        AdmissionToCourseBidEntity newAdmissionToCourseBidEntity = new AdmissionToCourseBidEntity();
        newAdmissionToCourseBidEntity.setUser(user);
        newAdmissionToCourseBidEntity.setCourse(course);
        newAdmissionToCourseBidEntity.setTimeCreated(LocalDateTime.now(ZoneId.systemDefault()));

        return crudAdmissionToCourseBidService.save(newAdmissionToCourseBidEntity);
    }
}
