package com.fitness.courses.http.attachment.service;

import org.springframework.stereotype.Service;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.attachment.utils.ContentStorageSectionsEnum;

@Service
public class AttachmentPermissionServiceImpl implements AttachmentPermissionService
{

    public String getUrlWithPermission(Long attachmentId, Long mainEntityId,
            ContentStorageSectionsEnum contentStorageSections)
    {
        String asd = "?id=...&entityId=...&contentSection=...";
        String baseUrl = "/attachment/get/...";
        return null;
    }

    public record AttachmentNeedPermission(AttachmentEntity attachmentEntity, boolean needPermission)
    {
    }
}
