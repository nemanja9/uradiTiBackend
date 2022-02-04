package com.uraditi.backend.service.keycloak;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakRoleService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public List<RoleRepresentation> findAll() {
        return keycloak
                .realm(realm)
                .roles()
                .list();
    }

    public void create(String name) {
        var role = new RoleRepresentation();
        role.setName(name);

        keycloak
                .realm(realm)
                .roles()
                .create(role);
    }

    public RoleRepresentation findByRoleName(String roleName) {
        return keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();
    }
}
