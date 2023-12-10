package com.fitness.courses.http.coach.course.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;
import com.fitness.courses.http.coach.course.model.entity.CourseCategoryEnum;
import com.fitness.courses.http.coach.course.model.entity.CourseStatus;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;

public class CourseAuthorGeneralInfoDto
{
    private Long id;

    private String title;

    private String shortDescription;

    private List<CourseCategoryEnum> categories;

    private String about;

    private String courseFor;

    private String requirements;

    private UserGeneralInfoDto author;

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

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public List<CourseCategoryEnum> getCategories()
    {
        return categories;
    }

    public void setCategories(List<CourseCategoryEnum> categories)
    {
        this.categories = categories;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public String getCourseFor()
    {
        return courseFor;
    }

    public void setCourseFor(String courseFor)
    {
        this.courseFor = courseFor;
    }

    public String getRequirements()
    {
        return requirements;
    }

    public void setRequirements(String requirements)
    {
        this.requirements = requirements;
    }

    public UserGeneralInfoDto getAuthor()
    {
        return author;
    }

    public void setAuthor(UserGeneralInfoDto author)
    {
        this.author = author;
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
