package com.fitness.courses.http.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;

@Repository
public interface AdmissionToCourseBidRepository extends JpaRepository<AdmissionToCourseBidEntity, Long>
{
    List<AdmissionToCourseBidEntity> findAllByUserId(Long userId);

    List<AdmissionToCourseBidEntity> findAllByCourseId(Long courseId);
}
