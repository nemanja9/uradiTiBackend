package com.uraditi.backend.exception.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.uraditi.backend.exception.ApiException;
import com.uraditi.backend.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebugErrorMessageDto extends ErrorMessageDto {

    private String embeddedMessage;

    //    @ApiModelProperty(notes = "The class name of the exception")
    private String className;

    private String embeddedClassName;

    //    @ApiModelProperty(notes = "The array of stack trace elements representing the stack trace of the exception")
    private StackTraceElement[] stackTrace;

    public DebugErrorMessageDto(UUID id, ApiException ex) {
        super(id, ex);
        this.className = ex.getClass().getName();
        if (ex.getCause() != null) {
            this.stackTrace = ex.getCause().getStackTrace();
            this.embeddedMessage = ex.getCause().getMessage();
            this.embeddedClassName = ex.getCause().getClass().getName();
        } else {
            this.stackTrace = ex.getStackTrace();
        }
    }

    public DebugErrorMessageDto(UUID id, ErrorCode code, Throwable ex) {
        super(id, code, ex);
        this.className = ex.getClass().getName();
        if (ex.getCause() != null) {
            this.stackTrace = ex.getCause().getStackTrace();
            this.embeddedMessage = ex.getCause().getMessage();
            this.embeddedClassName = ex.getCause().getClass().getName();
        } else {
            this.stackTrace = ex.getStackTrace();
        }
    }
}
