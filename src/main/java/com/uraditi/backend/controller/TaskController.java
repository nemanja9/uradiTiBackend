package com.uraditi.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Task controller")
@RequestMapping("/api/tasks")
@Tag(name = "Task controller", description = "Public task operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

}
