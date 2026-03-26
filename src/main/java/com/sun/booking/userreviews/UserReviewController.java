package com.sun.booking.userreviews;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.booking.common.httpresponse.BaseResponse;
import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.common.httpresponse.SuccessResponse;
import com.sun.booking.userreviews.dto.UserReviewDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User Reviews", description = "APIs for managing user reviews")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class UserReviewController {
  private final UserReviewService userReviewService;
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public BaseResponse<ListResponse> getAllReviews(int page, int size) {
    ListResponse result = userReviewService.getAllReviews(page, size);
    return new SuccessResponse<ListResponse>(result);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/tour/{tourId}")
  public BaseResponse<ListResponse> getReviewsByTourId(@PathVariable Long tourId, int page, int size) {
    ListResponse result = userReviewService.getReviewsByTourId(tourId, page, size);
    return new SuccessResponse<ListResponse>(result);
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/write")
  public BaseResponse<UserReviewDTO> writeReview(Authentication authentication, @RequestBody UserReviewDTO userReviewDTO) {
    UserReviewDTO savedReview = userReviewService.writeReview(userReviewDTO);
    return new SuccessResponse<UserReviewDTO>(savedReview);
  }
}
