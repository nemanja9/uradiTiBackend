package com.uraditi.backend.controller;

import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("User controller")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    // http://localhost:8080/swagger-ui/index.html

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.save(userDto));
    }
}
