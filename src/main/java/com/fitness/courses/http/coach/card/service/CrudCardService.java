package com.fitness.courses.http.coach.card.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.coach.card.model.entity.CardEntity;
import com.fitness.courses.http.user.model.User;

public interface CrudCardService
{
    CardEntity save(@NotNull CardEntity entity);

    void update(@NotNull CardEntity entity);

    void delete(@NotNull Long id);

    Optional<CardEntity> getById(@NotNull Long id);

    List<CardEntity> getAllByAuthor(@NotNull User user);
}
