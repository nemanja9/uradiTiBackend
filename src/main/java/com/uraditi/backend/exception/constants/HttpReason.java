package com.uraditi.backend.exception.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpReason {

    public static final String SUCCESS = "Success";
    public static final String CREATED = "Created";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String FORBIDDEN = "Forbidden";
    public static final String NOT_FOUND = "Not Found";
    public static final String NOT_ALLOWED = "Not Allowed";
    public static final String CONFLICT = "Conflict";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String NOT_IMPLEMENTED = "Not implemented";
}
