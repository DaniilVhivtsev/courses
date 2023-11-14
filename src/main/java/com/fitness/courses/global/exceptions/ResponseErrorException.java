package com.fitness.courses.global.exceptions;

public class ResponseErrorException extends RuntimeException {

    private String localizedMessageKey;
    private final int httpStatusCode;

    public ResponseErrorException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public ResponseErrorException(String message, Throwable cause, int httpStatusCode) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getLocalizedMessageKey() {
        return localizedMessageKey;
    }

    public void setLocalizedMessageKey(String localizedMessageKey) {
        this.localizedMessageKey = localizedMessageKey;
    }
}
