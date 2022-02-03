package com.uraditi.backend.exception.dto;

import com.uraditi.backend.exception.ConflictApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConflictErrorMessageDto extends ErrorMessageDto {
    //    @ApiModelProperty(notes = "The property", required = true)
    private String property;

    //    @ApiModelProperty(notes = "The suggested value", required = true)
    private String suggestedValue;

    public ConflictErrorMessageDto(UUID id, ConflictApiException ex) {
        super(id, ex);
        this.property = ex.getProperty();
        this.suggestedValue = ex.getSuggestedValue();
    }
}
