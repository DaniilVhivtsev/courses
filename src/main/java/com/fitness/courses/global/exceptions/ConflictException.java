package com.fitness.courses.global.exceptions;

import com.fitness.courses.global.constants.HTTPResponseConstants;

public class ConflictException extends ResponseErrorException
{
    private static final int STATUS_CODE = HTTPResponseConstants.CONFLICT;

    public ConflictException(String message)
    {
        super(message, STATUS_CODE);
    }

    public ConflictException(String message, Throwable cause)
    {
        super(message, cause, STATUS_CODE);
    }
}
