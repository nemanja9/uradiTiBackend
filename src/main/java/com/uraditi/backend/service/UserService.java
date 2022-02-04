package com.uraditi.backend.service;

import com.uraditi.backend.dto.AuthenticationResponseDto;
import com.uraditi.backend.dto.CreateUserDto;
import com.uraditi.backend.dto.KeycloakUserRequestDto;
import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.entity.UserEntity;
import com.uraditi.backend.exception.ApiExceptionFactory;
import com.uraditi.backend.repository.UserRepository;
import com.uraditi.backend.service.keycloak.UserKeycloakService;
import com.uraditi.backend.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final UserKeycloakService keycloakService;

    @Value("${com.uraditi.keycloak.user.role}")
    String userRole;

    @Value("${com.uraditi.created.user.url}")
    String urlCreatedUser;

    public UserDto save(CreateUserDto userDto) {
        // check if a user with the given email already exists in keycloak
        var usersInKeycloak = keycloakService.findForUsername(userDto.getEmail());
        if (usersInKeycloak.size() != 0) {
            throw ApiExceptionFactory.conflict("User with the given email already exists");
        }
        // save user into keycloak
        var keycloakResponse = keycloakService.create(KeycloakUserRequestDto.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .build());
        // checking if user is created
        if (keycloakResponse.getStatus() != HttpStatus.CREATED.value()) {
            throw ApiExceptionFactory.serverError("error with keycloak");
        }
        // taking created user id from response
        var keycloakCreatedId = keycloakResponse.getMetadata().get("Location").get(0).toString().replace(urlCreatedUser, "");
        // assigning user role to the new user
        keycloakService.assignRole(keycloakCreatedId, userRole);
        // saving user into our db
        var userToSave = UserEntity.builder()
                .id(UUID.fromString(keycloakCreatedId))
                .email(userDto.getEmail())
                .build();
        var saved = userRepository.save(userToSave);
        return ModelMapperUtils.map(saved, UserDto.class);
    }

    public List<UserDto> getAllUsers() {
        return ModelMapperUtils.mapAll(userRepository.findAll(), UserDto.class);
    }

    public AuthenticationResponseDto loginUser(UserDto user) {
        return keycloakService.loginUser(user);
    }
}
