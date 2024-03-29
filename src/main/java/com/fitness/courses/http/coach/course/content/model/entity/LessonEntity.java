package com.fitness.courses.http.coach.course.content.model.entity;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lesson_entity")
public class LessonEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int serialNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "logo_id", nullable = true)
    private AttachmentEntity icon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

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

    public AttachmentEntity getIcon()
    {
        return icon;
    }

    public void setIcon(AttachmentEntity icon)
    {
        this.icon = icon;
    }

    public ModuleEntity getModule()
    {
        return module;
    }

    public void setModule(ModuleEntity module)
    {
        this.module = module;
    }
}
