package com.uraditi.backend.exception.utils;

import com.uraditi.backend.exception.ApiException;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

@UtilityClass
public class ExceptionUtils {
    /**
     * Creates random number for error id
     *
     * @return {@link String} Error id
     */
    public static UUID createErrorId() {
        return UUID.randomUUID();
    }

    public static HttpStatus determineHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
        if (error instanceof ApiException) {
            return ((ApiException) error).getHttpStatus();
        }
        return error instanceof ResponseStatusException ? ((ResponseStatusException) error).getStatus() : responseStatusAnnotation.getValue("code", HttpStatus.class)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static String printStackTrace(Throwable error) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        error.printStackTrace(pw);
        return sw.toString();
    }

}
