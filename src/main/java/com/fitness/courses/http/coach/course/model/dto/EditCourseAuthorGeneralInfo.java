package com.fitness.courses.http.coach.course.model.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.model.entity.CourseCategoryEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Значения передаются через form-data. Также необходимо в хедере указать Content-Type равным multipart form-data")
public class EditCourseAuthorGeneralInfo
{
    @Schema(description = "Название курса")
    private String title;

    @Schema(description = "Краткое описание курса")
    private String shortDescription;

    @Schema(description = "Категории курса")
    private List<CourseCategoryEnum> categories;

    @Schema(description = "О чем курс")
    private String about;

    @Schema(description = "Для кого курс")
    private String courseFor;

    @Schema(description = "Требования для прохождения курса")
    private String requirements;

    @Schema(description = "Файл для загрузки", type = "string", format = "binary")
    private MultipartFile logo;

    public String getTitle()
    {
        return title;
    }

    public EditCourseAuthorGeneralInfo setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public EditCourseAuthorGeneralInfo setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
        return this;
    }

    public List<CourseCategoryEnum> getCategories()
    {
        return categories;
    }

    public EditCourseAuthorGeneralInfo setCategories(List<CourseCategoryEnum> categories)
    {
        this.categories = categories;
        return this;
    }

    public String getAbout()
    {
        return about;
    }

    public EditCourseAuthorGeneralInfo setAbout(String about)
    {
        this.about = about;
        return this;
    }

    public String getCourseFor()
    {
        return courseFor;
    }

    public EditCourseAuthorGeneralInfo setCourseFor(String courseFor)
    {
        this.courseFor = courseFor;
        return this;
    }

    public String getRequirements()
    {
        return requirements;
    }

    public EditCourseAuthorGeneralInfo setRequirements(String requirements)
    {
        this.requirements = requirements;
        return this;
    }

    public MultipartFile getLogo()
    {
        return logo;
    }

    public EditCourseAuthorGeneralInfo setLogo(MultipartFile logo)
    {
        this.logo = logo;
        return this;
    }
}
