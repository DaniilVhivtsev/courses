package com.fitness.courses.http.catalog.service;

import java.util.List;

import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;

public interface RestCatalogService
{
    List<CatalogNewCourseInfoDto> getNewCourses(Integer offset, Integer limit);

    List<CatalogPopularCourseInfoDto> getPopularCourses(Integer offset, Integer limit);

    List<CatalogBySearchValueCourseInfoDto> getCoursesBySearchValue(String searchValue, Integer offset, Integer limit);
}
