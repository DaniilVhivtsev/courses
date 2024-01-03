package com.fitness.courses.global.pagination;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;

@Service
public class PaginationValidatorImpl implements PaginationValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(PaginationValidatorImpl.class);

    @Override
    public void validateOffsetValue(Integer offset) throws ValidationException
    {
        if (offset == null)
        {
            final String message = "Pagination offset value can't be null";
            LOG.error(message);
            throw new ValidationException(message);
        }

        if (offset < 0)
        {
            final String message = "Pagination offset value can't be negative";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateLimitValue(Integer limit) throws ValidationException
    {
        if (limit == null)
        {
            final String message = "Pagination limit value can't be null";
            LOG.error(message);
            throw new ValidationException(message);
        }

        if (limit < 0)
        {
            final String message = "Pagination limit value can't be negative";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
