package com.fitness.courses.http.coach.card.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;

import org.apache.commons.io.FilenameUtils;

import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.card.mapper.CardMapper;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.card.model.entity.CardEntity;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.user.model.User;

@Service
public class CardServiceImpl implements CardService
{
    private final AuthService authService;
    private final AttachmentService attachmentService;
    private final CrudCardService crudCardService;

    @Autowired
    public CardServiceImpl(AuthService authService,
            AttachmentService attachmentService,
            CrudCardService crudCardService)
    {
        this.authService = authService;
        this.attachmentService = attachmentService;
        this.crudCardService = crudCardService;
    }

    @Transactional
    @Override
    public void addCard(NewCardDto newCardDto)
    {
        CardEntity cardEntity = CardMapper.toEntity(newCardDto);
        cardEntity.setAuthor(authService.getCurrentUserOrThrow());
        cardEntity.setImages(
                attachmentService.add(newCardDto.getImages().stream()
                        .map(file -> new MultipartFileWithExtension(
                                FileExtensionEnum.getEnum(FilenameUtils.getExtension(file.getOriginalFilename())),
                                file))
                        .toList())
        );
        cardEntity.setVideo(newCardDto.getVideo() != null
                ?
                attachmentService.add(new MultipartFileWithExtension(
                        FileExtensionEnum.getEnum(
                                FilenameUtils.getExtension(newCardDto.getVideo().getOriginalFilename())),
                        newCardDto.getVideo()))
                :
                null);

        crudCardService.save(cardEntity);
    }

    @Override
    public Optional<CardEntity> getCardOptional(@NotNull Long id)
    {
        return crudCardService.getById(id);
    }

    @Override
    public CardEntity getCardOrThrow(@NotNull Long id)
    {
        return crudCardService.getById(id)
                .orElseThrow(() -> new NotFoundException("Can't find card with id %s".formatted(id)));
    }

    @Override
    public List<CardEntity> getCurrentUserCards()
    {
        User currentUser = authService.getCurrentUserOrThrow();
        return crudCardService.getAllByAuthor(currentUser);
    }
}
