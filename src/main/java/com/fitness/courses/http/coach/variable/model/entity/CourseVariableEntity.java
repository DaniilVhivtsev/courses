package com.fitness.courses.http.coach.variable.model.entity;

import java.util.List;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.variable.model.entity.StudentVariableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "course_variable_entity",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "code",
                                "course_id" }
                )
        }
)
public class CourseVariableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String code;

    private VariableTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_variable_entity_id")
    private List<StudentVariableEntity> studentVariable;

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

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public VariableTypeEnum getType()
    {
        return type;
    }

    public void setType(VariableTypeEnum type)
    {
        this.type = type;
    }

    public CourseEntity getCourse()
    {
        return course;
    }

    public void setCourse(CourseEntity course)
    {
        this.course = course;
    }

    public List<StudentVariableEntity> getStudentVariable()
    {
        return studentVariable;
    }

    public void setStudentVariable(
            List<StudentVariableEntity> studentVariable)
    {
        this.studentVariable = studentVariable;
    }
}
