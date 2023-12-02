package com.fitness.courses.http.coach.card.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.coach.card.model.entity.CardEntity;

public interface CrudCardService
{
    @Transactional
    CardEntity save(@NotNull CardEntity entity);

    @Transactional
    void update(@NotNull CardEntity entity);

    @Transactional
    void delete(@NotNull Long id);

    @Transactional(readOnly = true)
    Optional<CardEntity> getById(@NotNull Long id);
}
