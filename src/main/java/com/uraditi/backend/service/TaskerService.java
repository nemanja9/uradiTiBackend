package com.uraditi.backend.service;

import com.uraditi.backend.dto.LocationDto;
import com.uraditi.backend.dto.TaskerCategoryDto;
import com.uraditi.backend.exception.ApiExceptionFactory;
import com.uraditi.backend.repository.CategoryRepository;
import com.uraditi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskerService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationService locationService;

    public List<TaskerCategoryDto> getTaskersByCategoryIdAndCity(Long categoryId, String city, String sortBy, double longitude, double latitude) {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> ApiExceptionFactory.notFound("Category not found"));
        switch (sortBy) {
            case "rating":
                return userRepository.findTaskersByCategoryAndCityDtoOrderedByRating(categoryId, city);
            case "price":
                return userRepository.findTaskersByCategoryAndCityDtoOrderedByPrice(categoryId, city);
            case "distance":
                var unsorted = userRepository.findTaskersByCategoryAndCityDto(categoryId, city);
                return locationService.sortTaskers(new LocationDto(latitude, longitude, "HOME"), unsorted);
            case "numberOfTasks":
                return userRepository.findTaskersByCategoryAndCityDtoOrderedByNumberOfTasks(categoryId, city);
            default:
                return userRepository.findTaskersByCategoryAndCityDto(categoryId, city);
        }
    }
}
