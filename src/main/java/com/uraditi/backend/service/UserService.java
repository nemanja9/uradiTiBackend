package com.uraditi.backend.service;

import com.uraditi.backend.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDto giveUserTest(){
        return UserDto.builder().id(123L).email("nemanja@nemanja.com").build();
    }
}
