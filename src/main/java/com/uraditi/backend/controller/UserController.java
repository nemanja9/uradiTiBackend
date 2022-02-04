package com.uraditi.backend.controller;

import com.uraditi.backend.dto.AuthenticationResponseDto;
import com.uraditi.backend.dto.CreateUserDto;
import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.dto.UserLoginDto;
import com.uraditi.backend.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController("User controller")
@RequestMapping("/api/users")
@Tag(name = "User controller", description = "Public user operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    // http://localhost:8080/swagger-ui/index.html

    @PostMapping("/register")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "200", description = "User registered")
    })
    public ResponseEntity<UserDto> saveUser(@RequestBody @NotNull CreateUserDto userToCreate) {
        return ResponseEntity.ok(userService.save(userToCreate));
    }

    @PostMapping("/log-in")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Username or password not correct"),
            @ApiResponse(responseCode = "200", description = "User logged in")
    })
    public ResponseEntity<AuthenticationResponseDto> logInUser(@RequestBody @NotNull UserLoginDto user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }
}
