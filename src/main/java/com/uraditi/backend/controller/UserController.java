package com.uraditi.backend.controller;

import com.uraditi.backend.dto.AuthenticationResponseDto;
import com.uraditi.backend.dto.CreateUserDto;
import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

@RestController("User controller")
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    // http://localhost:8080/swagger-ui/index.html

    @PostMapping("/save")
    @RolesAllowed("uradiTi_admin")
    public ResponseEntity<UserDto> saveUser(@RequestBody @NotNull CreateUserDto userToCreate) {
        return ResponseEntity.ok(userService.save(userToCreate));
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthenticationResponseDto> logInUser(@RequestBody @NotNull UserDto userToCreate) {
        return ResponseEntity.ok(userService.loginUser(userToCreate));
    }
}
