package com.studymate.service.category;

import com.studymate.dto.category.CategoryResponseDto;
import com.studymate.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories(){
        List<CategoryResponseDto> categories = categoryRepository.findAll()
                .stream().map(CategoryResponseDto::from)
                .collect(Collectors.toList());

        return categories;
    }
}
