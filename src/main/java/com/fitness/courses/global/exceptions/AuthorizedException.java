package com.fitness.courses.global.exceptions;

import com.fitness.courses.global.constants.HTTPResponseConstants;

public class AuthorizedException extends ResponseErrorException
{
    private static final int STATUS_CODE = HTTPResponseConstants.UNAUTHORIZED;

    public AuthorizedException(String message)
    {
        super(message, STATUS_CODE);
    }

    public AuthorizedException(String message, Throwable cause)
    {
        super(message, cause, STATUS_CODE);
    }
}
