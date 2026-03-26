package com.sun.booking.categories;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.categories.dto.CategoryDTO;
import com.sun.booking.categories.entity.Category;
import com.sun.booking.categories.entity.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  public List<CategoryDTO> getAllCategories() {
    List<Category> categories = categoryRepository.findAllActive();
    return categories.stream()
        .map(category -> modelMapper.map(category, CategoryDTO.class))
        .collect(Collectors.toList());
  }

  public ListResponse getListCategories(int page, int size) {
    // start from page 1 for client, but PageRequest starts from 0
    page = page > 0 ? page - 1 : 0;
    Page<Category> categoryPage = categoryRepository.findAllActivePage(PageRequest.of(page, size));
    List<CategoryDTO> categoryDTOs = categoryPage.getContent().stream()
        .map(category -> modelMapper.map(category, CategoryDTO.class))
        .collect(Collectors.toList());

    return ListResponse.builder()
        .content(categoryDTOs)
        .curPage(categoryPage.getNumber())
        .curPageSize(categoryPage.getSize())
        .totalElements(categoryPage.getTotalElements())
        .totalPages(categoryPage.getTotalPages())
        .build();
  }

  public Category getCategoryDetailById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
  }

    public CategoryDTO getCategoryDTODetailById(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));  
    return modelMapper.map(category, CategoryDTO.class);
  }

  public CategoryDTO createCategory(CategoryDTO input) {
    if (categoryRepository.existsByName(input.getName())) {
      throw new RuntimeException("Category with name '" + input.getName() + "' already exists");
    }

    Category category = new Category();
    category.setName(input.getName());
    category.setDescription(input.getDescription());

    Category savedCategory = categoryRepository.save(category);
    return modelMapper.map(savedCategory, CategoryDTO.class);
  }

  public CategoryDTO updateCategory(Long id, CategoryDTO input) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    category.setName(input.getName());
    category.setDescription(input.getDescription());

    Category updatedCategory = categoryRepository.save(category);
    return modelMapper.map(updatedCategory, CategoryDTO.class);
  }

  public boolean deleteCategory(Long id) {
    int deletedCount = categoryRepository.deleteCategoryById(id);
    return deletedCount > 0;
  }

}
