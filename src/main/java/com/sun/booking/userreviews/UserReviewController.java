package com.sun.booking.userreviews;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserReviewController {
  private final UserReviewService userReviewService;
  
}
