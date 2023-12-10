package com.fitness.courses.http.coach.course.model.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.model.entity.CourseCategoryEnum;

public class EditCourseAuthorGeneralInfo
{
    private Long id;

    private String title;

    private String shortDescription;

    private List<CourseCategoryEnum> categories;

    private String about;

    private String courseFor;

    private String requirements;

    private MultipartFile logo;

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

    public MultipartFile getLogo()
    {
        return logo;
    }

    public void setLogo(MultipartFile logo)
    {
        this.logo = logo;
    }
}
