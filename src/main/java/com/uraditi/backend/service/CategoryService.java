package com.uraditi.backend.service;

import com.uraditi.backend.dto.CategoryDto;
import com.uraditi.backend.entity.CategoryEntity;
import com.uraditi.backend.exception.ApiExceptionFactory;
import com.uraditi.backend.repository.CategoryRepository;
import com.uraditi.backend.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAll() {
        return ModelMapperUtils.mapAll(categoryRepository.findAll(), CategoryDto.class);
    }

    public CategoryDto getById(Long id) {
        return ModelMapperUtils.map(categoryRepository.findById(id).orElseThrow(() -> ApiExceptionFactory.notFound("Category with id " + id + " not found")), CategoryDto.class);
    }

    public CategoryDto save(CategoryDto category) {
        category.setId(null);
        return ModelMapperUtils.map(categoryRepository.save(ModelMapperUtils.map(category, CategoryEntity.class)), CategoryDto.class);
    }

    public void delete(Long categoryId) {
        getById(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    public String getDescription(Long id) {
        return getById(id).getDescription();
    }
}
