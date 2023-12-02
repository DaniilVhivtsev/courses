package com.fitness.courses.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.http.attachment.service.AttachmentService;

@RestController
@RequestMapping("/attachment")
public class AttachmentController
{
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService)
    {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/get")
    public ResponseEntity<UrlResource> getFile(@RequestParam String bucketName, @RequestParam String fileKey)
            throws IOException
    {
        UrlResource resource = attachmentService.getFile(bucketName, fileKey);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=" + resource.getFilename().replace(" ", "_"))
                        "attachment; filename=" + "asdas-asdas-asds.png".replace(" ", "_"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
