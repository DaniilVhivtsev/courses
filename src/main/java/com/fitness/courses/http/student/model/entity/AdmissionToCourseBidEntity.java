package com.fitness.courses.http.student.model.entity;

import java.time.LocalDateTime;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "admission_to_the_course_bid")
public class AdmissionToCourseBidEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeCreated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
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

    public LocalDateTime getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public CourseEntity getCourse()
    {
        return course;
    }

    public void setCourse(CourseEntity course)
    {
        this.course = course;
    }
}
