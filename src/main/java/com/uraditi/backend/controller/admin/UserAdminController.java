package com.uraditi.backend.controller.admin;

import com.uraditi.backend.dto.CreateUserDto;
import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

@RestController("Admin user controller")
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAdminController {

    private final UserService userService;

    @PostMapping("/save")
    @RolesAllowed("uradiTi_admin")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "200", description = "User registered")
    })
    public ResponseEntity<UserDto> saveUser(@RequestBody @NotNull CreateUserDto userToCreate) {
        return ResponseEntity.ok(userService.save(userToCreate));
    }

}
