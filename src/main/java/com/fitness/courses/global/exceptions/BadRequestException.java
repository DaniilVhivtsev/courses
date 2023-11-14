package com.fitness.courses.global.exceptions;

import com.fitness.courses.global.constants.HTTPResponseConstants;

public class BadRequestException extends ResponseErrorException
{
    private static final int STATUS_CODE = HTTPResponseConstants.BAD_REQUEST;

    public BadRequestException(String message)
    {
        super(message, STATUS_CODE);
    }

    public BadRequestException(String message, Throwable cause)
    {
        super(message, cause, STATUS_CODE);
    }
}
