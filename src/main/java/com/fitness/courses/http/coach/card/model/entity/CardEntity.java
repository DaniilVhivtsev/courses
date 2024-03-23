package com.fitness.courses.http.coach.card.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "card_entity")
public class CardEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true, length = 2048)
    private String description;

    @Column(nullable = true)
    private String muscleGroupsDescription;

    @Column(nullable = true)
    private String inventoryDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<AttachmentEntity> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "video_id", nullable = true)
    private AttachmentEntity video;

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMuscleGroupsDescription()
    {
        return muscleGroupsDescription;
    }

    public void setMuscleGroupsDescription(String muscleGroupsDescription)
    {
        this.muscleGroupsDescription = muscleGroupsDescription;
    }

    public String getInventoryDescription()
    {
        return inventoryDescription;
    }

    public void setInventoryDescription(String inventoryDescription)
    {
        this.inventoryDescription = inventoryDescription;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public List<AttachmentEntity> getImages()
    {
        return images;
    }

    public void setImages(List<AttachmentEntity> images)
    {
        this.images = images;
    }

    public AttachmentEntity getVideo()
    {
        return video;
    }

    public void setVideo(AttachmentEntity video)
    {
        this.video = video;
    }
}
