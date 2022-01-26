package com.uraditi.backend.controller;

import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("User controller")
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    // http://localhost:8080/swagger-ui/index.html

    @GetMapping("/person/{id}")
    public UserDto idk(@PathVariable ("id") String userId){
        return userService.giveUserTest();
    }
}
