package com.uraditi.backend.exception.dto;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
class ValidationError {

    //    @ApiModelProperty(notes = "Affected field")
    private final String field;

    //    @ApiModelProperty(notes = "Validation message")
    private final String message;

    ValidationError(FieldError error) {
        this.field = error.getField();
        this.message = error.getDefaultMessage();
    }
}
