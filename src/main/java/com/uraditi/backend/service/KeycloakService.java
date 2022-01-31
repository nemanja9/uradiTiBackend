package com.uraditi.backend.service;

import com.auth0.jwt.JWT;
import com.uraditi.backend.client.KeycloakClient;
import com.uraditi.backend.client.KeycloakTokenChecker;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakService {
    private final KeycloakTokenChecker keycloakTokenChecker;
    private final KeycloakClient keycloakClient;

    private String token;

    @Value("${com.uraditi.keycloak.grant.type}")
    private String grantType;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String secret;


    private void checkExpiredToken() {
        if (token == null || token.isEmpty() || JWT.decode(token).getExpiresAt().before(new Date())) {
            System.out.println("token is expired");
            var so = keycloakTokenChecker.getNewToken("grant_type=" + grantType + "&client_id=" + clientId + "&client_secret=" + secret);
            token = so.getToken_type() + " " + so.getAccess_token();
        }
    }


    public Object delete(String id) {
        checkExpiredToken();
        return keycloakClient.delete(token, id);
    }

    public String save(UserRepresentation user) {
        checkExpiredToken();
        return keycloakClient.save(user);
    }

    public List<UserRepresentation> findByUsername(String username, boolean exact) {
        checkExpiredToken();
        return keycloakClient.findByUsername(token, username, exact);
    }
}
