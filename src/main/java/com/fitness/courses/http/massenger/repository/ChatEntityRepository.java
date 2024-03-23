package com.fitness.courses.http.massenger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.massenger.model.entity.ChatEntity;

@Repository
public interface ChatEntityRepository extends JpaRepository<ChatEntity, Long>
{
    @Query("SELECT e FROM ChatEntity e WHERE :interlocutorId IN elements(e.interlocutorsIds)")
    List<ChatEntity> findByInterlocutorId(@Param("interlocutorId") Long interlocutorId);
}
