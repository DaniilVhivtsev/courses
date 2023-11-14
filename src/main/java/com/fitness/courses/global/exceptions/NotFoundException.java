package com.fitness.courses.global.exceptions;

import com.fitness.courses.global.constants.HTTPResponseConstants;

public class NotFoundException extends ResponseErrorException
{
    private static final int STATUS_CODE = HTTPResponseConstants.NOT_FOUND;

    public NotFoundException(String message)
    {
        super(message, STATUS_CODE);
    }

    public NotFoundException(String message, Throwable cause)
    {
        super(message, cause, STATUS_CODE);
    }
}
