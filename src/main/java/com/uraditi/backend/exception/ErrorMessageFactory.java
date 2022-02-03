package com.uraditi.backend.exception;

import com.uraditi.backend.exception.dto.ConflictErrorMessageDto;
import com.uraditi.backend.exception.dto.DebugErrorMessageDto;
import com.uraditi.backend.exception.dto.ErrorMessageDto;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
@Setter
public class ErrorMessageFactory {

    public ErrorMessageDto createErrorDto(boolean includeException, UUID id, ApiException ex) {
        if (!includeException)
            return new ErrorMessageDto(id, ex);
        else
            return new DebugErrorMessageDto(id, ex);
    }

    public ErrorMessageDto createErrorDto(boolean includeException, UUID id, ErrorCode code, Throwable ex) {
        if (!includeException)
            return new ErrorMessageDto(id, code, ex);
        else
            return new DebugErrorMessageDto(id, code, ex);
    }

    public ErrorMessageDto createErrorDto(UUID id, ConflictApiException ex) {

        return new ConflictErrorMessageDto(id, ex);
    }

}
