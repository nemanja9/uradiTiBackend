package com.uraditi.backend.service.keycloak;

import com.uraditi.backend.dto.KeycloakUserRequestDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class UserKeycloakService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


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

    public List<UserRepresentation> findForUsername(String username) {
        return keycloak
                .realm(realm)
                .users()
                .search(username);
    }

    private UserRepresentation prepareUserRepresentation(KeycloakUserRequestDto request, CredentialRepresentation cR) {
        var newUser = new UserRepresentation();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(newUser.getEmail());
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

}
