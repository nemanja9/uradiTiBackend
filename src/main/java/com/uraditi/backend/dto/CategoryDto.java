package com.uraditi.backend.dto;

import lombok.Data;

@Data
public class CategoryDto {

    private String name;
    private String description;
    private Long id;
    private boolean licence;
}
