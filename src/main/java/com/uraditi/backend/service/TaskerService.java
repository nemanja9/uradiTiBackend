package com.uraditi.backend.service;

import com.uraditi.backend.dto.TaskerCategoryDto;
import com.uraditi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskerService {

    private final UserRepository userRepository;

    public List<TaskerCategoryDto> getTaskersByCategoryId(Long categoryId, String city, String orderBy) {
        var unsorted = userRepository.findTaskersByCategoryAndCityDto(categoryId, city);
        // todo sortiranje na osnovu parametra
//        switch (orderBy)
        return userRepository.findTaskersByCategoryAndCityDto(categoryId, city);
    }
}
