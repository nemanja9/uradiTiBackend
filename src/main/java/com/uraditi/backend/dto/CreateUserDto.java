package com.uraditi.backend.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateUserDto {

    private String email;
    private String password;
}
