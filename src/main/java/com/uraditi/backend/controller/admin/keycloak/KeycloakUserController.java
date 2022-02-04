package com.uraditi.backend.controller.admin.keycloak;

import com.uraditi.backend.dto.KeycloakUserRequestDto;
import com.uraditi.backend.dto.SuccesDto;
import com.uraditi.backend.service.UserService;
import com.uraditi.backend.service.keycloak.KeycloakUserService;
import io.swagger.v3.oas.annotations.Operation;
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

    private final KeycloakUserService keycloakUserService;
    private final UserService userService;

    @PostMapping("/create")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<Response> createUser(@RequestBody @NotNull KeycloakUserRequestDto userToCreate) {
        keycloakUserService.create(userToCreate);
        return ResponseEntity.ok(keycloakUserService.create(userToCreate));
    }

    @GetMapping("/all")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> getAllKeycloakUsers() {
        return ResponseEntity.ok(keycloakUserService.findAll());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<Response> deleteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(keycloakUserService.delete(id));
    }

    @GetMapping("/username/{username}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(keycloakUserService.findByUsername(username));
    }

    @GetMapping("/id/{id}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<UserRepresentation> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(keycloakUserService.findById(id));
    }

    @GetMapping("/import")
    @Operation(description = "Import users who are in keycloak but not in db into the db")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<SuccesDto> importKeycloakUsersIntoDb() {
        userService.importIntoDb();
        return ResponseEntity.ok(new SuccesDto(true));
    }

}
