package com.uraditi.backend.controller.admin.keycloak;

import com.uraditi.backend.dto.KeycloakUserRequestDto;
import com.uraditi.backend.service.keycloak.UserKeycloakService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController("Keycloak user controller")
@Tag(name = "Keycloak user controller", description = "Keycloak user operations")
@RequestMapping("/api/keycloak/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakUserController {

    private final UserKeycloakService userKeycloakService;

    @PostMapping("/create")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<Response> createUser(@RequestBody @NotNull KeycloakUserRequestDto userToCreate) {
        userKeycloakService.create(userToCreate);
        return ResponseEntity.ok(userKeycloakService.create(userToCreate));
    }

    @GetMapping("/all")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> getAllKeycloakUsers() {
        return ResponseEntity.ok(userKeycloakService.findAll());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<Response> deleteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userKeycloakService.delete(id));
    }

    @GetMapping("/{username}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userKeycloakService.findForUsername(username));
    }

}
