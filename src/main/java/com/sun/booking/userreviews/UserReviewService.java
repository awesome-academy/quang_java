package com.sun.booking.userreviews;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.userreviews.dto.UserReviewDTO;
import com.sun.booking.userreviews.entity.UserReview;
import com.sun.booking.userreviews.entity.UserReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserReviewService {
  private final UserReviewRepository userReviewRepository;
  private final ModelMapper modelMapper;

  public ListResponse getReviewsByUserId(Long userId, int page, int size) {
    PageRequest pageable = PageRequest.of(page, size);
    Page<UserReview> reviews = userReviewRepository.findByUserId(userId, pageable);
    List<UserReviewDTO> content = reviews.stream()
        .map(review -> modelMapper.map(review, UserReviewDTO.class))
        .collect(Collectors.toList());

    return ListResponse.builder()
        .content(content)
        .curPage(reviews.getNumber())
        .curPageSize(reviews.getSize())
        .totalElements(reviews.getTotalElements())
        .totalPages(reviews.getTotalPages())
        .build();
  }
}
