package com.uraditi.backend.controller;

import com.uraditi.backend.client.KeycloakClient;
import com.uraditi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("User controller")
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@ApiResponses({
//        @ApiResponse(responseCode = "500", description = errorMessageDto.class)
//})
public class UserController {

    private final UserService userService;
    private final KeycloakClient keycloakClient;

    // http://localhost:8080/swagger-ui/index.html

    @PostMapping("/create")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity createUser(@RequestParam @NotNull @NotEmpty String username,
                                     @RequestParam @NotNull @NotEmpty String password) {
        return ResponseEntity.ok(userService.createUser(username, password));
    }

    @GetMapping("/all")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(keycloakClient.getAllUsers());
    }

    @GetMapping("/{username}")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<List<UserRepresentation>> findUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("uradiTi_admin")
    public Object deleteUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.delete(id));
    }


}
