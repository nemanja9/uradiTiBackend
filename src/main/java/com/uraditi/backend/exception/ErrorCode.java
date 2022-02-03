package com.uraditi.backend.exception;

/**
 * Error codes returned in exceptions
 * Do not remove any of error codes
 */
public enum ErrorCode {
    GENERIC_ERROR,
    NOT_FOUND,
    ALREADY_LOCKED,
    INTERNAL_ERROR,
    BAD_USER,
    BAD_REQUEST,
    INVALID_CHANNEL_TYPE,
    TIMED_OUT,
    UNAUTHORIZED,
    SERVICE_UNAVAILABLE,
    ERROR_COMMUNICATING_WITH_SERVICE,
    INTERNAL_SERVER_ERROR,
    FORBIDDEN,
    METHOD_NOT_ALLOWED,
    NOT_ACCEPTABLE,
    REQUEST_TIMEOUT,
    CONFLICT,
    GONE,
    LENGTH_REQUIRED,
    PRECONDITION_FAILED,
    PAYLOAD_TOO_LARGE,
    REQUEST_ENTITY_TOO_LARGE,
    URI_TOO_LONG,
    REQUEST_URI_TOO_LONG,
    UNSUPPORTED_MEDIA_TYPE,
    REQUESTED_RANGE_NOT_SATISFIABLE,
    EXPECTATION_FAILED,
    METHOD_FAILURE,
    DESTINATION_LOCKED,
    UNPROCESSABLE_ENTITY,
    LOCKED,
    FAILED_DEPENDENCY,
    PRECONDITION_REQUIRED,
    TOO_MANY_REQUESTS,
    REQUEST_HEADER_FIELDS_TOO_LARGE,
    NOT_IMPLEMENTED,
    BAD_GATEWAY,
    GATEWAY_TIMEOUT,
    HTTP_VERSION_NOT_SUPPORTED,
    UNKNOWN,
    ALREADY_EXISTS,
    GENERIC_CLIENT_ERROR
}
