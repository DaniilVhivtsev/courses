package com.fitness.courses.http.attachment.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;
import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.attachment.service.AttachmentPermissionService;
import com.fitness.courses.http.attachment.utils.ContentStorageSectionsEnum;

@Component
public class AttachmentMapper
{
    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder attachmentInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(AttachmentInfoDto.class, AttachmentEntity.class);
            }
        };

        MAPPER.addMapping(attachmentInfoBuilder);
    }

    private final AttachmentPermissionService attachmentPermissionService;

    @Autowired
    public AttachmentMapper(
            AttachmentPermissionService attachmentPermissionService)
    {
        this.attachmentPermissionService = attachmentPermissionService;
    }

    public AttachmentInfoDto toDtoWithPermission(AttachmentEntity attachmentEntity, Long mainEntityId,
            ContentStorageSectionsEnum contentStorageSections)
    {
        AttachmentInfoDto dto = MAPPER.map(attachmentEntity, AttachmentInfoDto.class);
//        dto.setUrl(attachmentPermissionService.);
        return null;
    }
}
