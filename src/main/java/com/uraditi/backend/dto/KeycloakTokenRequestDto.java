package com.uraditi.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class KeycloakTokenRequestDto implements Serializable {
    private String client_secret;
    private String grant_type;
    private String client_id;
}
