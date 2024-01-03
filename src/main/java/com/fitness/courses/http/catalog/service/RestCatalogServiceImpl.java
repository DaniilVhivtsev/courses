package com.fitness.courses.http.catalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.pagination.PaginationValidator;
import com.fitness.courses.http.catalog.mapper.CatalogMapper;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.coach.course.service.CourseService;

@Service
public class RestCatalogServiceImpl implements RestCatalogService
{
    private final CourseService courseService;
    private final PaginationValidator paginationValidator;
    private final CatalogMapper catalogMapper;
    private final CatalogValidator catalogValidator;

    @Autowired
    public RestCatalogServiceImpl(
            CourseService courseService,
            PaginationValidator paginationValidator,
            CatalogMapper catalogMapper,
            CatalogValidator catalogValidator)
    {
        this.courseService = courseService;
        this.paginationValidator = paginationValidator;
        this.catalogMapper = catalogMapper;
        this.catalogValidator = catalogValidator;
    }

    @Override
    public List<CatalogNewCourseInfoDto> getNewCourses(Integer offset, Integer limit)
    {
        paginationValidator.validateOffsetValue(offset);
        paginationValidator.validateLimitValue(limit);

        return courseService.getNewCourses(offset, limit).stream()
                .map(catalogMapper::toCatalogNewCourseInfoDto)
                .toList();
    }

    @Override
    public List<CatalogPopularCourseInfoDto> getPopularCourses(Integer offset, Integer limit)
    {
        paginationValidator.validateOffsetValue(offset);
        paginationValidator.validateLimitValue(limit);

        return courseService.getPopularCourses(offset, limit).stream()
                .map(catalogMapper::toCatalogPopularCourseInfoDto)
                .toList();
    }

    @Override
    public List<CatalogBySearchValueCourseInfoDto> getCoursesBySearchValue(String searchValue, Integer offset, Integer limit)
    {
        paginationValidator.validateOffsetValue(offset);
        paginationValidator.validateLimitValue(limit);

        catalogValidator.validateSearchValue(searchValue);

        return courseService.findAllByKeyword(searchValue, offset, limit).stream()
                .map(catalogMapper::toCatalogBySearchValueCourseInfoDto)
                .toList();
    }
}
