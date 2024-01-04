package com.fitness.courses.http.catalog.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.StageInfoDto;

public interface RestCatalogService
{
    List<StageInfoDto> getLessonStagesInfo(@NotNull Long courseId, @NotNull Long lessonId);

    List<CatalogNewCourseInfoDto> getNewCourses(Integer offset, Integer limit);

    CourseInfoDto getCourseInfo(Long courseId);

    List<CatalogPopularCourseInfoDto> getPopularCourses(Integer offset, Integer limit);

    List<CatalogBySearchValueCourseInfoDto> getCoursesBySearchValue(String searchValue, Integer offset, Integer limit);
}
