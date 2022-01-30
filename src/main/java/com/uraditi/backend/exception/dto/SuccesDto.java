package com.uraditi.backend.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccesDto {
    //    @ApiModelProperty(value = "Success")
    private boolean success = true;
}
