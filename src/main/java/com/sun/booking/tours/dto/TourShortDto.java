package com.sun.booking.tours.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourShortDto {
  @NotBlank(message = "Tour ID is required")
  private Long id;
  private Long categoryId;
  private String title;
  private String description;
  private BigDecimal price;
}
