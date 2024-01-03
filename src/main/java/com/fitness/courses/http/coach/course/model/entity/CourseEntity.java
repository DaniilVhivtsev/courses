package com.fitness.courses.http.coach.course.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CourseCategoryEnum.class)
    private List<CourseCategoryEnum> categories = new ArrayList<>();

    @Column(nullable = true)
    private String about;

    @Column(nullable = true)
    private String courseFor;

    @Column(nullable = true)
    private String requirements;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private LocalDateTime dateTimeCreated;

    @OneToOne()
    @JoinColumn(name = "logo_id", nullable = true)
    private AttachmentEntity logo;

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

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
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

    public AttachmentEntity getLogo()
    {
        return logo;
    }

    public void setLogo(AttachmentEntity logo)
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
