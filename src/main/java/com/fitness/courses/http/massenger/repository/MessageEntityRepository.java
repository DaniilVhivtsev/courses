package com.fitness.courses.http.massenger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.massenger.model.entity.MessageEntity;

@Repository
public interface MessageEntityRepository extends JpaRepository<MessageEntity, Long>
{
    List<MessageEntity> findAllByChatEntityIdOrderByDateTimeAsc(Long chatEntityId);
}
