package com.fitness.courses.http.coach.card.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;

import org.apache.commons.io.FilenameUtils;

import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.card.mapper.CardMapper;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.card.model.entity.CardEntity;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

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
        cardEntity.setUser(authService.getCurrentUserOrThrow());
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
}
