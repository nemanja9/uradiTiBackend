package com.uraditi.backend.service;

import com.uraditi.backend.dto.CreateTaskDto;
import com.uraditi.backend.dto.TaskDto;
import com.uraditi.backend.dto.enums.TaskStatusEnum;
import com.uraditi.backend.entity.CategoryEntity;
import com.uraditi.backend.entity.TaskEntity;
import com.uraditi.backend.entity.UserEntity;
import com.uraditi.backend.exception.ApiExceptionFactory;
import com.uraditi.backend.repository.CategoryRepository;
import com.uraditi.backend.repository.TaskRepository;
import com.uraditi.backend.repository.UserRepository;
import com.uraditi.backend.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TaskDto create(CreateTaskDto taskDto) {

        var client = userRepository.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())).orElseThrow(() -> ApiExceptionFactory.notFound("User not found " +
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        UserEntity tasker = null;
        if (taskDto.getTaskerId() != null) {
            tasker = userRepository.findById(UUID.fromString(taskDto.getTaskerId())).orElseThrow(() -> ApiExceptionFactory.notFound("User not found " + taskDto.getTaskerId()));
        }
        CategoryEntity category = null;
        if (taskDto.getCategoryId() != null) {
            category = categoryRepository.findById(taskDto.getCategoryId()).orElseThrow(() -> ApiExceptionFactory.notFound("Category not found " + taskDto.getCategoryId()));
        }
        var toBeSaved = TaskEntity.builder()
                .taskStatus(TaskStatusEnum.TO_DO)
                .tasker(tasker)
                .category(category)
                .client(client)
                .latitude(taskDto.getLatitude())
                .longitude(taskDto.getLongitude())
                .timeCreated(new Timestamp(System.currentTimeMillis()))
                .build();
        var saved = taskRepository.save(toBeSaved);
        return ModelMapperUtils.map(saved, TaskDto.class);
    }
}
