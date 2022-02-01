package com.uraditi.backend.controller;

import com.uraditi.backend.dto.KeycloakUserRequestDto;
import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.service.UserService;
import com.uraditi.backend.service.keycloak.UserKeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController("User controller")
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final UserKeycloakService userKeycloakService;

    // http://localhost:8080/swagger-ui/index.html

    @GetMapping("/all")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> getAllKeycloakUsers() {
        return ResponseEntity.ok(userKeycloakService.findAll());
    }

    @PostMapping("/create")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<Response> createUser(@RequestBody @NotNull KeycloakUserRequestDto userToCreate) {
        userKeycloakService.create(userToCreate);
        return ResponseEntity.ok(userKeycloakService.create(userToCreate));
    }

    @PostMapping("/save")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<UserDto> saveUser(@RequestBody @NotNull UserDto userToCreate) {
        return ResponseEntity.ok(userService.save(userToCreate));
    }

    @GetMapping("/{username}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userKeycloakService.findForUsername(username));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<Response> deleteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userKeycloakService.delete(id));
    }


}
