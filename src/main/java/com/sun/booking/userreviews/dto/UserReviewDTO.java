package com.sun.booking.userreviews.dto;

import com.sun.booking.tours.dto.TourShortDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewDTO {
  private Long userId;
  private TourShortDto tour;
  private Integer rating;
  private String content;
}
