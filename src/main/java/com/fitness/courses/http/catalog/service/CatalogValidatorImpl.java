package com.fitness.courses.http.catalog.service;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;

@Service
public class CatalogValidatorImpl implements CatalogValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CatalogValidatorImpl.class);

    @Override
    public void validateSearchValue(String searchValue) throws ValidationException
    {
        if (StringUtils.isBlank(searchValue))
        {
            final String message = "Search value is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
