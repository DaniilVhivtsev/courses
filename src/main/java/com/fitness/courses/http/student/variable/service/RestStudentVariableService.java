package com.fitness.courses.http.student.variable.service;

import java.util.List;

import com.fitness.courses.http.student.variable.model.dto.CourseVariableWithStudentValue;
import com.fitness.courses.http.student.variable.model.dto.StudentVariableUpdatedValue;
import com.fitness.courses.http.student.variable.model.info.CourseVariableWithStudentValueInfo;

public interface RestStudentVariableService
{
    void updateStudentVariableValues(Long courseId, List<StudentVariableUpdatedValue> studentVariableUpdatedValues);

    boolean checkNeedToFillValuesInVariables(Long courseId);

    List<CourseVariableWithStudentValue> getCourseVariablesWithStudentValues(Long courseId);

    List<CourseVariableWithStudentValueInfo> getCourseVariablesWithStudentValuesInfo(Long courseId);
}
