package com.uraditi.backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class KeycloakTokenResponseDto implements Serializable {
    private String access_token;
    private String token_type;
    private String scope;
    private int expires_in;
    private int refresh_expires_in;
    private int not_before_policy;
}
