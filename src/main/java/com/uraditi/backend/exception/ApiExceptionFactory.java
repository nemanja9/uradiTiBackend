package com.uraditi.backend.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiExceptionFactory {

    /**
     * Returns a not implemented API exception
     *
     * @return {@link ApiException} with {@link ErrorCode} NOT_IMPLEMENTED
     */
    public static ApiException notImplemented() {
        return new ApiException(HttpStatus.NOT_IMPLEMENTED, ErrorCode.NOT_IMPLEMENTED, "Not implemented");
    }

    public static ApiException notFound(String message) {
        return new ApiException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message);
    }

    public static ApiException conflict(String message) {
        return new ApiException(HttpStatus.CONFLICT, ErrorCode.CONFLICT, message);
    }

    public static ApiException badRequest(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message);
    }

    public static ApiException serverError(String message) {
        return new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, message);
    }

    public static ApiException genericError(HttpStatus httpStatus, String message) {
        return new ApiException(httpStatus, ErrorCode.GENERIC_CLIENT_ERROR, message);
    }
}
