package com.sun.booking.userreviews.dto;

import com.sun.booking.tours.dto.TourShortDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewDTO {
  @NotBlank(message = "User ID is required")
  private Long userId;
  private TourShortDto tour;
  @NotBlank(message = "Rating is required")
  @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
  private Integer rating;
  @NotBlank(message = "Content is required")
  private String content;
}
