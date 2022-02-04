package com.uraditi.backend.controller;

import com.uraditi.backend.dto.CreateTaskDto;
import com.uraditi.backend.dto.TaskDto;
import com.uraditi.backend.service.TaskService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

@RestController("Task controller")
@RequestMapping("/api/tasks")
@Tag(name = "Task controller", description = "Public task operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/save")
    @RolesAllowed({"uradiTi_user", "uradiTi_admin"})
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "200", description = "User registered")
    })
    public ResponseEntity<TaskDto> createTask(@RequestBody @NotNull CreateTaskDto taskDto) {
        return ResponseEntity.ok(taskService.create(taskDto));
    }
}
