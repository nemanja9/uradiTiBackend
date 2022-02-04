package com.uraditi.backend.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreateTaskDto {

    Long categoryId;
    String taskerId;
    Timestamp timeCreated;
    // rating that the tasker got for the job done
    Double latitude;
    Double longitude;
}
