package com.sun.booking.categories;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.booking.categories.dto.CategoryDTO;
import com.sun.booking.common.httpresponse.BaseResponse;
import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.common.httpresponse.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category Management", description = "Endpoints for managing categories, including creation, retrieval, updating, and deletion of category information")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/")
  public BaseResponse<ListResponse> getListCategory(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size) {
    ListResponse result = categoryService.getListCategories(page, size);
    return new SuccessResponse<ListResponse>(result);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/all")
  public BaseResponse<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> result = categoryService.getAllCategories();
    return new SuccessResponse<List<CategoryDTO>>(result);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/detail/{id}")
  public BaseResponse<CategoryDTO> getCategoryDetail(@PathVariable Long id) {
    CategoryDTO result = categoryService.getCategoryDTODetailById(id);
    return new SuccessResponse<CategoryDTO>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create")
  public BaseResponse<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO input) {
    CategoryDTO result = categoryService.createCategory(input);
    return new SuccessResponse<CategoryDTO>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/update/{id}")
  public BaseResponse<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO input) {
    CategoryDTO result = categoryService.updateCategory(id, input);
    return new SuccessResponse<CategoryDTO>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  public BaseResponse<Boolean> deleteCategory(@PathVariable Long id) {
    boolean result = categoryService.deleteCategory(id);
    return new SuccessResponse<Boolean>(result);
  }

}
