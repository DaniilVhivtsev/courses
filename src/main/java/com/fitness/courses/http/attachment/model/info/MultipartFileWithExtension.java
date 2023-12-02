package com.fitness.courses.http.attachment.model.info;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

public record MultipartFileWithExtension(@NotNull FileExtensionEnum fileExtension, @NotNull MultipartFile file)
{
}
