package com.fitness.courses.global.exceptions;

import com.fitness.courses.global.constants.HTTPResponseConstants;

public class ValidationException extends ResponseErrorException
{
    private static final int STATUS_CODE = HTTPResponseConstants.BAD_REQUEST;

    public ValidationException(String message)
    {
        super(message, STATUS_CODE);
    }

    public ValidationException(String message, Throwable cause)
    {
        super(message, cause, STATUS_CODE);
    }
}
