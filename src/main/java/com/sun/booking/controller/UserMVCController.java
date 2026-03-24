package com.sun.booking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.security.CustomUserDetails;
import com.sun.booking.userreviews.UserReviewService;
import com.sun.booking.userreviews.dto.UserReviewDTO;
import com.sun.booking.users.UserService;
import com.sun.booking.users.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserMVCController {
  
  private final UserService userService;
  private final UserReviewService userReviewService;

  @GetMapping("/user/info")
  public String getUserInfo(Authentication authentication, Model model,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size) {
    // get custom user details from authentication
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    Long userId = Long.parseLong(userDetails.getId());
    UserDTO userDTO = userService.getUserInfo(userId);
    Map<String, Object> user = new HashMap<>();
    user.put("username", userDTO.getUsername());
    user.put("email", userDTO.getEmail());
    user.put("socialId", userDTO.getSocialId());
    user.put("socialType", userDTO.getSocialType());
    model.addAttribute("user", user);

    ListResponse userReviewData = userReviewService.getReviewsByUserId(userId, page, size);
    List<Map<String, Object>> reviewMaps = ((List<?>) userReviewData.getContent()).stream()
        .map(item -> {
          UserReviewDTO dto = (UserReviewDTO) item;
          Map<String, Object> map = new HashMap<>();
          map.put("userId", dto.getUserId());
          if (dto.getTour() != null) {
            Map<String, Object> tourMap = new HashMap<>();
            tourMap.put("categoryId", dto.getTour().getCategoryId());
            tourMap.put("title", dto.getTour().getTitle());
            tourMap.put("description", dto.getTour().getDescription());
            tourMap.put("price", dto.getTour().getPrice());
            map.put("tour", tourMap);
          }
          map.put("rating", dto.getRating());
          map.put("content", dto.getContent());
          return map;
        })
        .collect(Collectors.toList());
    Map<String, Object> userReviews = new HashMap<>();
    userReviews.put("content", reviewMaps);
    userReviews.put("curPage", userReviewData.getCurPage());
    userReviews.put("curPageSize", userReviewData.getCurPageSize());
    userReviews.put("totalElements", userReviewData.getTotalElements());
    userReviews.put("totalPages", userReviewData.getTotalPages());
    model.addAttribute("reviews", userReviews);

    return "user-info";
  }
  
}
