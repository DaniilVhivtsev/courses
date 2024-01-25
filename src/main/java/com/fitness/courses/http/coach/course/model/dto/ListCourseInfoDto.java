package com.fitness.courses.http.coach.course.model.dto;

import java.time.LocalDateTime;

import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;
import com.fitness.courses.http.coach.course.model.entity.CourseStatus;

public class ListCourseInfoDto
{
    private Long id;

    private String title;

    private LocalDateTime dateTimeCreated;

    private AttachmentInfoDto logo;

    private CourseStatus status;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public LocalDateTime getDateTimeCreated()
    {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated)
    {
        this.dateTimeCreated = dateTimeCreated;
    }

    public AttachmentInfoDto getLogo()
    {
        return logo;
    }

    public void setLogo(AttachmentInfoDto logo)
    {
        this.logo = logo;
    }

    public CourseStatus getStatus()
    {
        return status;
    }

    public void setStatus(CourseStatus status)
    {
        this.status = status;
    }
}
