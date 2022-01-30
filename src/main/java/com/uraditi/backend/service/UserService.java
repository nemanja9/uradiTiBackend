package com.uraditi.backend.service;

import com.uraditi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public Object createUser(String username, String passwordRequest) {
        var password = preparePassword(passwordRequest);
        var user = prepareUserRepresentation(username, password);

        return keycloakService.save(user);
    }

    private UserRepresentation prepareUserRepresentation(String username, CredentialRepresentation password) {
        var newUser = new UserRepresentation();
        newUser.setUsername(username);
        newUser.setCredentials(List.of(password));
        newUser.setEnabled(true);
        return newUser;
    }

    private CredentialRepresentation preparePassword(String password) {
        var cr = new CredentialRepresentation();
        cr.setTemporary(false);
        cr.setType(CredentialRepresentation.PASSWORD);
        cr.setValue(password);
        return cr;
    }

    public Object delete(String id) {
        return keycloakService.delete(id);
    }

    public List<UserRepresentation> findByUsername(String username) {
        return keycloakService.findByUsername(username, true);
    }
}
