package com.fitness.courses.global.exceptions;

import com.fitness.courses.global.constants.HTTPResponseConstants;

public class AlreadyExistsException extends ResponseErrorException
{
    private static final int STATUS_CODE = HTTPResponseConstants.CONFLICT;

    public AlreadyExistsException(String message)
    {
        super(message, STATUS_CODE);
    }

    public AlreadyExistsException(String message, Throwable cause)
    {
        super(message, cause, STATUS_CODE);
    }
}
