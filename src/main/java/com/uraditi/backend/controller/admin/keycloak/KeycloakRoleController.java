package com.uraditi.backend.controller.admin.keycloak;

import com.uraditi.backend.dto.SuccesDto;
import com.uraditi.backend.service.keycloak.KeycloakRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("Keycloak role controller")
@Tag(name = "Keycloak role controller", description = "Keycloak role operations")
@RequestMapping("/api/keycloak/roles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakRoleController {

    private final KeycloakRoleService keycloakRoleService;

    @PostMapping("/create")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<SuccesDto> createRole(@RequestBody @NotNull String name) {
        keycloakRoleService.create(name);
        return ResponseEntity.ok(new SuccesDto(true));
    }

    @GetMapping("/all")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<RoleRepresentation>> getAllKeycloakRoles() {
        return ResponseEntity.ok(keycloakRoleService.findAll());
    }

    @GetMapping("/{rolename}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<RoleRepresentation> getByRoleName(@PathVariable("rolename") String roleName) {
        return ResponseEntity.ok(keycloakRoleService.findByRoleName(roleName));
    }

}
