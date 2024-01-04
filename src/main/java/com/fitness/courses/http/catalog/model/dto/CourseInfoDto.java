package com.fitness.courses.http.catalog.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fitness.courses.http.catalog.model.dto.content.ModuleInfoDto;
import com.fitness.courses.http.coach.course.model.entity.CourseCategoryEnum;

public class CourseInfoDto
{
    private Long id;

    private String title;

    private String shortDescription;

    private List<CourseCategoryEnum> categories;

    private String about;

    private String courseFor;

    private String requirements;

    private Long authorId;

    private String authorFullName;

    private String authorShortDescription;

    private LocalDateTime dateTimeCreated;

    private String iconImgUrl;

    private Double rating;

    private Integer numberOfPeople;

    private boolean isFree;

    private Integer price;

    private boolean userIsRegisteredForTheCourse;

    private List<ModuleInfoDto> modules;

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

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorFullName()
    {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName)
    {
        this.authorFullName = authorFullName;
    }

    public String getAuthorShortDescription()
    {
        return authorShortDescription;
    }

    public void setAuthorShortDescription(String authorShortDescription)
    {
        this.authorShortDescription = authorShortDescription;
    }

    public LocalDateTime getDateTimeCreated()
    {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated)
    {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getIconImgUrl()
    {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl)
    {
        this.iconImgUrl = iconImgUrl;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Integer getNumberOfPeople()
    {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople)
    {
        this.numberOfPeople = numberOfPeople;
    }

    public boolean isFree()
    {
        return isFree;
    }

    public void setFree(boolean free)
    {
        isFree = free;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public boolean isUserIsRegisteredForTheCourse()
    {
        return userIsRegisteredForTheCourse;
    }

    public void setUserIsRegisteredForTheCourse(boolean userIsRegisteredForTheCourse)
    {
        this.userIsRegisteredForTheCourse = userIsRegisteredForTheCourse;
    }

    public List<ModuleInfoDto> getModules()
    {
        return modules;
    }

    public void setModules(List<ModuleInfoDto> modules)
    {
        this.modules = modules;
    }
}
