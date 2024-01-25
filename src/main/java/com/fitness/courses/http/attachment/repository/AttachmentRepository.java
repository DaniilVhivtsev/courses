package com.fitness.courses.http.attachment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long>
{
}
