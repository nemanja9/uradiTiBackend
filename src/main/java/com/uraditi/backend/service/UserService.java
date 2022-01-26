package com.uraditi.backend.service;

import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.entity.UserEntity;
import com.uraditi.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public UserDto giveUserTest(){
        return UserDto.builder().id(123L).email("nemanja@nemanja.com").build();
    }

    public UserDto save(UserDto userDto) {
        var saved = userRepository.save(modelMapper.map(userDto, UserEntity.class));
        return modelMapper.map(saved, UserDto.class);
    }
}
