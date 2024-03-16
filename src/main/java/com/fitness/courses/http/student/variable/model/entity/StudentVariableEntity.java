package com.fitness.courses.http.student.variable.model.entity;

import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "student_variable_entity",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_course_student",
                        columnNames = {
                                "course_variable_id",
                                "student_id" }
                )
        }
)
public class StudentVariableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value_float")
    private Float value;

    @ManyToOne
    @JoinColumn(name = "course_variable_id")
    private CourseVariableEntity courseVariable;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    public Long getId()
    {
        return id;
    }

    public StudentVariableEntity setId(Long id)
    {
        this.id = id;
        return this;
    }

    public Float getValue()
    {
        return value;
    }

    public StudentVariableEntity setValue(Float value)
    {
        this.value = value;
        return this;
    }

    public CourseVariableEntity getCourseVariable()
    {
        return courseVariable;
    }

    public StudentVariableEntity setCourseVariable(CourseVariableEntity courseVariable)
    {
        this.courseVariable = courseVariable;
        return this;
    }

    public StudentEntity getStudent()
    {
        return student;
    }

    public StudentVariableEntity setStudent(StudentEntity student)
    {
        this.student = student;
        return this;
    }
}
