package com.uraditi.backend.dto;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateUserDto {

    private String email;
    private String password;
    private UUID id;
    private String jwtToken;
}
