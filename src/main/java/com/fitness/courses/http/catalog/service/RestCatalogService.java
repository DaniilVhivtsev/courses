package com.fitness.courses.http.catalog.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.catalog.model.dto.AuthorCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.LessonWithStagesInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.StageInfoDto;

public interface RestCatalogService
{
    LessonWithStagesInfoDto getLessonStagesInfo(@NotNull Long courseId, @NotNull Long lessonId);

    List<AuthorCourseInfoDto> getAuthorCourses(@NotNull Long authorId);

    List<CatalogNewCourseInfoDto> getNewCourses(Integer offset, Integer limit);

    CourseInfoDto getCourseInfo(Long courseId);

    List<CatalogPopularCourseInfoDto> getPopularCourses(Integer offset, Integer limit);

    List<CatalogBySearchValueCourseInfoDto> getCoursesBySearchValue(String searchValue, Integer offset, Integer limit);
}
