package com.fitness.courses.http.coach.card.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.attachment.repository.AttachmentRepository;
import com.fitness.courses.http.coach.card.model.entity.CardEntity;
import com.fitness.courses.http.coach.card.repository.CardRepository;

@Service
public class CrudCardServiceImpl implements CrudCardService
{
    private final CardRepository cardRepository;

    @Autowired
    public CrudCardServiceImpl(CardRepository cardRepository)
    {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public CardEntity save(@NotNull CardEntity entity)
    {
        return cardRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(@NotNull CardEntity entity)
    {
        this.save(entity);
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id)
    {
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CardEntity> getById(@NotNull Long id)
    {
        return cardRepository.findById(id);
    }
}
