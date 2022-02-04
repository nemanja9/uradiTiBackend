package com.uraditi.backend.dto;

import com.uraditi.backend.dto.enums.TaskStatusEnum;
import lombok.*;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TaskDto {
    Long id;
    CategoryDto category;
    UserDto client;
    UserDto tasker;
    Timestamp timeCreated;
    Timestamp timeStarted;
    Timestamp timeFinished;
    TaskStatusEnum taskStatus;
    // rating that the tasker got for the job done
    Double taskerRating;
    Double latitude;
    Double longitude;
    // review that the tasker got for the job done
    String taskerReview;
    // review that the client got for their job
    String clientReview;
}
