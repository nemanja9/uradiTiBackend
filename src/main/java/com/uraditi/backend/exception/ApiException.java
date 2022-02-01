package com.uraditi.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Api Exception class
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;
    private ErrorMessageDto originalErrorMessage = null;

    private ApiException(Throwable cause, HttpStatus httpStatus, ErrorCode errorCode,
                         String errorMessage) {
        super(errorMessage, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public ApiException(Throwable cause, HttpStatus httpStatus, ErrorCode errorCode,
                        String errorMessage, ErrorMessageDto originalErrorMessage) {
        super(errorMessage, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.originalErrorMessage = originalErrorMessage;
    }

    public ApiException(HttpStatus httpStatus, ErrorCode errorCode, String errorMessage) {
        this(null, httpStatus, errorCode, errorMessage);
    }


}
