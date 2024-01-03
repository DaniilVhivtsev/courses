package com.fitness.courses.http.student.model.entity;

import java.util.Set;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.model.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "course_id"})
        }
)
public class StudentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToOne
    @JoinColumn(name = "approved_bid_id")
    private AdmissionToCourseBidEntity approvedBid;

    @ElementCollection
    @CollectionTable(name = "student_done_stage_uuids", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "done_stage_uuid")
    private Set<String> doneStageUuids;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public AdmissionToCourseBidEntity getApprovedBid()
    {
        return approvedBid;
    }

    public void setApprovedBid(AdmissionToCourseBidEntity approvedBid)
    {
        this.approvedBid = approvedBid;
    }

    public Set<String> getDoneStageUuids()
    {
        return doneStageUuids;
    }

    public void setDoneStageUuids(Set<String> doneStageUuids)
    {
        this.doneStageUuids = doneStageUuids;
    }
}
