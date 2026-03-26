package com.sun.booking.userreviews;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.tours.TourService;
import com.sun.booking.tours.entity.Tour;
import com.sun.booking.userreviews.dto.UserReviewDTO;
import com.sun.booking.userreviews.entity.UserReview;
import com.sun.booking.userreviews.entity.UserReviewRepository;
import com.sun.booking.users.UserService;
import com.sun.booking.users.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserReviewService {
  private final UserReviewRepository userReviewRepository;
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final TourService tourService;

  public ListResponse getAllReviews(int page, int size) {
    // start from page 1 for client, but PageRequest starts from 0
    page = page > 0 ? page - 1 : 0;
    PageRequest pageable = PageRequest.of(page, size);
    Page<UserReview> reviews = userReviewRepository.findAll(pageable);
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

  public ListResponse getReviewsByTourId(Long tourId, int page, int size) {
    // start from page 1 for client, but PageRequest starts from 0
    page = page > 0 ? page - 1 : 0;
    PageRequest pageable = PageRequest.of(page, size);
    Page<UserReview> reviews = userReviewRepository.findByTourId(tourId, pageable);
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

  @Transactional(rollbackOn = Exception.class)
  public UserReviewDTO writeReview(UserReviewDTO userReviewDTO) {
    UserReview userReview = new UserReview();

    User user = userService.getUserById(userReviewDTO.getUserId());
    Tour tour = tourService.getTourById(userReviewDTO.getTour().getId());

    userReview.setRating(userReviewDTO.getRating());
    userReview.setContent(userReviewDTO.getContent());
    userReview.setUser(user);
    userReview.setTour(tour);
    UserReview savedReview = userReviewRepository.save(userReview);
    return modelMapper.map(savedReview, UserReviewDTO.class);
  }
}
