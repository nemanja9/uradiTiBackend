package com.uraditi.backend.controller;

import com.uraditi.backend.dto.TaskerCategoryDto;
import com.uraditi.backend.service.TaskerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController("Tasker controller")
@RequestMapping("/api/taskers")
@Tag(name = "Tasker controller", description = "Public tasker operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskerController {
    private final TaskerService taskerService;

    @Operation(description = "Provides a list of taskers based on city and category id. Sorting options: 'rating', 'price', 'location','numberOfTasks'")
    @PostMapping("/category-city")
    @RolesAllowed({"uradiTi_user", "uradiTi_admin"})
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Category id not found"),
            @ApiResponse(responseCode = "200", description = "User registered")
    })
    public ResponseEntity<List<TaskerCategoryDto>> getTaskersByCategoryIdAndCity(@RequestParam("categoryId") Long categoryId,
                                                                                 @RequestParam("city") String city,
                                                                                 @RequestParam("sortBy") String sortBy,
                                                                                 @RequestParam("longitude") double longitude,
                                                                                 @RequestParam("latitude") double latitude) {
        return ResponseEntity.ok(taskerService.getTaskersByCategoryIdAndCity(categoryId, city, sortBy, longitude, latitude));
    }
}
