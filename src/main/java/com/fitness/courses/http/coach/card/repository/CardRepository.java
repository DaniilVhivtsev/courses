package com.fitness.courses.http.coach.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.card.model.entity.CardEntity;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long>
{
}
