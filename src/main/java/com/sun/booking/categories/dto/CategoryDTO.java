package com.sun.booking.categories.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
  private Long id;
  @NotNull(message = "Name is required")
  private String name;
  @NotNull(message = "Description is required")
  private String description;
}
