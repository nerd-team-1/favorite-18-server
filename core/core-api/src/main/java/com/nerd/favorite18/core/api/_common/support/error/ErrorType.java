package com.nerd.favorite18.core.api._common.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public enum ErrorType {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, ErrorCode.E1404, "User is not fonded.", LogLevel.ERROR),

    QNA_NOT_FOUND(HttpStatus.BAD_REQUEST, ErrorCode.E3404, "Qna is not fonded.", LogLevel.ERROR),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, ErrorCode.E2000, "Token is invalided.", LogLevel.ERROR),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, ErrorCode.E2001, "Token is expired.", LogLevel.ERROR),
    TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, ErrorCode.E2002, "An unexpected error with token.", LogLevel.ERROR),
    AUTHORIZATION_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, ErrorCode.E2003, "No token in the authentication header.", LogLevel.ERROR),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Bad Request", LogLevel.ERROR),
    FORBIDDEN(HttpStatus.FORBIDDEN, ErrorCode.E403, "Don't have permission to request in that API.", LogLevel.ERROR),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "Null point error.", LogLevel.ERROR),
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.", LogLevel.ERROR);

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {

        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

}
