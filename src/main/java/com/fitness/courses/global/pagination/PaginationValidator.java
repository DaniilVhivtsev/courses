package com.fitness.courses.global.pagination;

import com.fitness.courses.global.exceptions.ValidationException;

public interface PaginationValidator
{
    void validateOffsetValue(Integer offset) throws ValidationException;

    void validateLimitValue(Integer limit) throws ValidationException;
}
