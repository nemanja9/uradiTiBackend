package com.uraditi.backend.dto;

import com.uraditi.backend.dto.enums.UserStatusEnum;
import com.uraditi.backend.dto.enums.UserTypeEnum;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    UUID id;
    String email;
    Set<TaskDto> tasksAsClient;
    Set<TaskDto> tasksAsTasker;
    UserTypeEnum userType;
    String description;
    // todo slika??
    UserStatusEnum userStatus;
    Double rating;
    Double latitude;
    Double longitude;
    String phone;
    String password;
}
