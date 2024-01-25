package com.fitness.courses.http.catalog.service;

import com.fitness.courses.global.exceptions.ValidationException;

public interface CatalogValidator
{
    void validateSearchValue(String searchValue) throws ValidationException;
}
