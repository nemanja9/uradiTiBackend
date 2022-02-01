package com.uraditi.backend.exception;

//import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDto {

    private UUID id;

    //    @ApiModelProperty(notes = "The error code", required = true)
    private ErrorCode code;

    //    @ApiModelProperty(notes = "The detail message of the exception")
    private String message;

    //    @ApiModelProperty(notes = "The stack trace as String")
    private String content;

    //    @ApiModelProperty(notes = "Original error message")
    private Object originalErrorMessage;

    public ErrorMessageDto(UUID id, ApiException ex) {
        this.id = id;
        this.code = ex.getErrorCode();
        this.message = ex.getMessage();
        this.originalErrorMessage = ex.getOriginalErrorMessage();
        if (ex.getOriginalErrorMessage() == null) {
            this.content = ExceptionUtils.printStackTrace(ex);
        }
    }

    public ErrorMessageDto(UUID id, ErrorCode code, Throwable ex) {
        this.id = id;
        this.code = code;
        this.message = ex.getMessage();
        if (ex.getCause() != null) {
            this.content = ExceptionUtils.printStackTrace(ex.getCause());
        } else {
            this.content = ExceptionUtils.printStackTrace(ex);
        }
    }

}
