package com.uraditi.backend.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionErrorMessageDto extends ErrorMessageDto {
    //    @ApiModelProperty(notes = "Suggestion value")
    private String suggestion;
}
