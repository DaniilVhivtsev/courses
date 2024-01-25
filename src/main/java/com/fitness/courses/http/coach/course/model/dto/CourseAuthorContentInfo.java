package com.fitness.courses.http.coach.course.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.module.CourseAuthorModuleInfo;

public class CourseAuthorContentInfo
{
    private List<CourseAuthorModuleInfo> modules = new ArrayList<>();

    public List<CourseAuthorModuleInfo> getModules()
    {
        return modules;
    }

    public void setModules(List<CourseAuthorModuleInfo> modules)
    {
        this.modules = modules;
    }
}
