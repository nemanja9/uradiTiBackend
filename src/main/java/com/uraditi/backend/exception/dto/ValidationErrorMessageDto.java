package com.uraditi.backend.exception.dto;

import com.uraditi.backend.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorMessageDto extends ErrorMessageDto {

    //    @ApiModelProperty(notes = "List of validation errors")
    private List<ValidationError> validationErrors = new ArrayList<>();

    public ValidationErrorMessageDto(UUID id, MethodArgumentNotValidException ex) {
        super(id, ErrorCode.BAD_REQUEST, ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            validationErrors.add(new ValidationError(error));
        }
    }
}
