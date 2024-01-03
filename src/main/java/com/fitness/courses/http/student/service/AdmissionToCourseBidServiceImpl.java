package com.fitness.courses.http.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
