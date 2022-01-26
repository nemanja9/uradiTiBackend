package com.uraditi.backend.service;

import com.uraditi.backend.dto.UserDto;
import com.uraditi.backend.entity.UserEntity;
import com.uraditi.backend.repository.UserRepository;
import com.uraditi.backend.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public UserDto giveUserTest() {
        return UserDto.builder().id(123L).email("nemanja@nemanja.com").build();
    }

    public UserDto save(UserDto userDto) {
        var saved = userRepository.save(ModelMapperUtils.map(userDto, UserEntity.class));
        return ModelMapperUtils.map(saved, UserDto.class);
    }

    public List<UserDto> getAllUsers() {
        return ModelMapperUtils.mapAll(userRepository.findAll(), UserDto.class);
    }
}
