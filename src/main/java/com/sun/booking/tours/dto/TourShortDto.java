package com.sun.booking.tours.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourShortDto {
  private Long categoryId;
  private String title;
  private String description;
  private BigDecimal price;
}
