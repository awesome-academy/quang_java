package com.sun.booking.tours.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDTO {
  private Long id;
  @NotNull(message = "Category ID is required")
  private Long categoryId;
  @NotNull(message = "Title is required")
  private String title;
  @NotNull(message = "Description is required")
  private String description;
  @NotNull(message = "Price is required")
  private BigDecimal price;
  private BigDecimal rating;
  private Integer reviewsCount;
  @NotNull(message = "Image URL is required")
  @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL starting with http or https")
  private String imageUrl;
}
