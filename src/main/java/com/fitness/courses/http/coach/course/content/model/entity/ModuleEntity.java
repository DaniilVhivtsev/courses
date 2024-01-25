package com.fitness.courses.http.coach.course.content.model.entity;

import java.util.Objects;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "module_entity")
public class ModuleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int serialNumber;

    @Column(columnDefinition="TEXT", length = 256)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

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

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public CourseEntity getCourse()
    {
        return course;
    }

    public void setCourse(CourseEntity course)
    {
        this.course = course;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ModuleEntity that = (ModuleEntity)o;
        return serialNumber == that.serialNumber && Objects.equals(id, that.id) && Objects.equals(title,
                that.title) && Objects.equals(description, that.description) && Objects.equals(course,
                that.course);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, title, serialNumber, description, course);
    }
}
