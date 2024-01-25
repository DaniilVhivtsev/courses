package com.fitness.courses.http.student.model.entity;

import java.util.Set;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.model.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
                @UniqueConstraint(columnNames = { "user_id", "course_id" })
        }
)
public class StudentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_bid_id")
    private AdmissionToCourseBidEntity approvedBid;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_done_stage_and_set_uuids", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "done_stage_or_set_uuid")
    private Set<String> doneStageAndSetUuids;

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

    public Set<String> getDoneStageAndSetUuids()
    {
        return doneStageAndSetUuids;
    }

    public void setDoneStageAndSetUuids(Set<String> doneStageAndSetUuids)
    {
        this.doneStageAndSetUuids = doneStageAndSetUuids;
    }
}
