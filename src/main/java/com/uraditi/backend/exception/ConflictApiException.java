package com.uraditi.backend.exception;

import com.uraditi.backend.exception.dto.ErrorMessageDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictApiException extends ApiException {
    private final String property;

    private final String suggestedValue;

    public ConflictApiException(String property, String suggestedValue, String errorMessage) {
        super(HttpStatus.CONFLICT, ErrorCode.CONFLICT, errorMessage);
        this.property = property;
        this.suggestedValue = suggestedValue;
    }

    public ConflictApiException(String property, String suggestedValue, String errorMessage, ErrorMessageDto originalErrorMessage) {
        super(null, HttpStatus.CONFLICT, ErrorCode.CONFLICT, errorMessage, originalErrorMessage);
        this.property = property;
        this.suggestedValue = suggestedValue;
    }
}
