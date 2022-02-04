package com.uraditi.backend.controller.admin;

import com.uraditi.backend.dto.CategoryDto;
import com.uraditi.backend.dto.SuccesDto;
import com.uraditi.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController("Category controller")
@RequestMapping("/api/categories")
@Tag(name = "Category controller", description = "Category operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    @Operation(description = "Returns list of all categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/create")
    @Operation(description = "Create a new category")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.save(categoryDto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Delete a category based on id")
    public ResponseEntity<SuccesDto> deleteCategory(@PathVariable("id") @NotNull @NotEmpty Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok(new SuccesDto(true));
    }


}
