package com.fitness.courses.http.coach.course.content.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;

import com.fitness.courses.http.coach.course.content.model.entity.stage.AbstractStageContent;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stage_entity")
public class StageEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int serialNumber;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lesson_id", nullable = true)
    private LessonEntity lesson = null;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private List<AbstractStageContent> stageContent = new ArrayList<>();

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

    public LessonEntity getLesson()
    {
        return lesson;
    }

    public void setLesson(LessonEntity lesson)
    {
        this.lesson = lesson;
    }

    public List<AbstractStageContent> getStageContent()
    {
        return stageContent;
    }

    public void setStageContent(
            List<AbstractStageContent> stageContent)
    {
        this.stageContent = stageContent;
    }
}
