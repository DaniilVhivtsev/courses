package com.fitness.courses.http.greeting.model.dto;

import java.util.List;

import com.fitness.courses.http.student.variable.model.dto.CourseVariableWithStudentValue;

public class StudentGreetingContent
{
    private String courseGreetingTitle;
    private List<CourseVariableWithStudentValue> courseVariablesWithStudentValues;

    public String getCourseGreetingTitle()
    {
        return courseGreetingTitle;
    }

    public StudentGreetingContent setCourseGreetingTitle(String courseGreetingTitle)
    {
        this.courseGreetingTitle = courseGreetingTitle;
        return this;
    }

    public List<CourseVariableWithStudentValue> getCourseVariablesWithStudentValues()
    {
        return courseVariablesWithStudentValues;
    }

    public StudentGreetingContent setCourseVariablesWithStudentValues(
            List<CourseVariableWithStudentValue> courseVariablesWithStudentValues)
    {
        this.courseVariablesWithStudentValues = courseVariablesWithStudentValues;
        return this;
    }
}
