package com.uraditi.backend.service.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uraditi.backend.client.RestClient;
import com.uraditi.backend.dto.AuthenticationResponseDto;
import com.uraditi.backend.dto.KeycloakUserRequestDto;
import com.uraditi.backend.dto.SuccesDto;
import com.uraditi.backend.dto.UserLoginDto;
import com.uraditi.backend.exception.ApiExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final Keycloak keycloak;
    private final RestClient restClient;
    private final ObjectMapper mapper;
    private final KeycloakRoleService keycloakRoleService;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${com.uraditi.token.retrieval.url}")
    private String tokenUrl;


    public List<UserRepresentation> findAll() {
        return keycloak
                .realm(realm)
                .users()
                .list();
    }

    public Response create(KeycloakUserRequestDto request) {
        var password = preparePasswordRepresentation(request.getPassword());
        var user = prepareUserRepresentation(request, password);
        return keycloak
                .realm(realm)
                .users()
                .create(user);
    }

    public Response delete(String id) {
        return keycloak.realm(realm)
                .users()
                .delete(id);
    }

    public List<UserRepresentation> findByUsername(String username) {
        return keycloak
                .realm(realm)
                .users()
                .search(username);
    }

    public UserRepresentation findById(String id) {
        return keycloak
                .realm(realm)
                .users()
                .get(id)
                .toRepresentation();
    }

    public AuthenticationResponseDto loginUser(UserLoginDto userDto) {
        ObjectNode auth = mapper.createObjectNode();
        auth.put("grant_type", "password");
        auth.put("client_id", clientId);
        auth.put("username", userDto.getEmail());
        auth.put("password", userDto.getPassword());
        auth.put("client_secret", clientSecret);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var params = Map.of(
                "grant_type", "password",
                "client_id", clientId,
                "username", userDto.getEmail(),
                "password", userDto.getPassword(),
                "client_secret", clientSecret);

        ResponseEntity<AuthenticationResponseDto> authResponse = restClient.postWithHeaders(AuthenticationResponseDto.class, tokenUrl, params, httpHeaders);
        return authResponse.getBody();
    }

    private UserRepresentation prepareUserRepresentation(KeycloakUserRequestDto request, CredentialRepresentation cR) {
        var newUser = new UserRepresentation();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getUsername());
        newUser.setEmailVerified(false);
        newUser.setEnabled(true);
        newUser.setCredentials(List.of(cR));
        return newUser;
    }


    private CredentialRepresentation preparePasswordRepresentation(String password) {
        var cR = new CredentialRepresentation();
        cR.setTemporary(false);
        cR.setType(CredentialRepresentation.PASSWORD);
        cR.setValue(password);
        return cR;
    }

    /**
     * Assigns user a role based on user id and role name, throws 404 if role doesn't exist
     *
     * @param userId
     * @param roleName
     */
    public void assignRole(String userId, String roleName) {
        var role = keycloakRoleService.findByRoleName(roleName);
        if (role == null) {
            throw ApiExceptionFactory.notFound("Role " + roleName + " not found");
        }
        keycloak
                .realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    public SuccesDto sendEmailConfirmation(String userId) {
        keycloak.realm(realm)
                .users()
                .get(userId)
                .sendVerifyEmail();
        return new SuccesDto(true);
    }

    public SuccesDto sendPasswordResetEmail(String userId) {
        keycloak.realm(realm)
                .users()
                .get(userId)
                .resetPasswordEmail();
        return new SuccesDto(true);
    }
}
