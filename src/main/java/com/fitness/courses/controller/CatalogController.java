package com.fitness.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.catalog.service.RestCatalogService;

@RestController
@RequestMapping("/public/course")
public class CatalogController
{
    private final RestCatalogService restCatalogService;

    @Autowired
    public CatalogController(RestCatalogService restCatalogService)
    {
        this.restCatalogService = restCatalogService;
    }

    @GetMapping("/new/all")
    public ResponseEntity<?> getNewCourses(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        try
        {
            return new ResponseEntity<List<CatalogNewCourseInfoDto>>(
                    restCatalogService.getNewCourses(offset, limit),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping("/popular/all")
    public ResponseEntity<?> getPopularCourses(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        try
        {
            return new ResponseEntity<List<CatalogPopularCourseInfoDto>>(
                    restCatalogService.getPopularCourses(offset, limit),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping("/searchBy")
    public ResponseEntity<?> getCoursesBySearchValue(
            @RequestParam("searchValue") String searchValue,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        try
        {
            return new ResponseEntity<List<CatalogBySearchValueCourseInfoDto>>(
                    restCatalogService.getCoursesBySearchValue(searchValue, offset, limit),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }
}
