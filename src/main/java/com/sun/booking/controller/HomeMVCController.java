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
import com.sun.booking.tours.TourService;
import com.sun.booking.tours.dto.TourDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeMVCController {

  private final TourService tourService;

  @GetMapping("/home")
  public String homePage(Authentication authentication, Model model,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String filter) {
    String username = authentication.getName();
    model.addAttribute("username", username);
    String role = authentication.getAuthorities().stream()
            .map(auth -> auth.getAuthority())
            .filter(auth -> auth.startsWith("ROLE_"))
            .findFirst()
            .orElse("ROLE_USER");
    if(role.equals("ROLE_USER") || role.equals("ROLE_GUEST")) {
      // get list tour
      ListResponse tourList = tourService.getAllTours(page, size);
      model.addAttribute("currentFilter", filter);
      // Convert DTOs to Maps to avoid Thymeleaf's com.sun.* package access restriction
      List<Map<String, Object>> tours = ((List<?>) tourList.getContent()).stream()
              .map(item -> {
                TourDTO dto = (TourDTO) item;
                Map<String, Object> map = new HashMap<>();
                map.put("id", dto.getId());
                map.put("title", dto.getTitle());
                map.put("description", dto.getDescription());
                map.put("price", dto.getPrice());
                map.put("rating", dto.getRating());
                map.put("reviewsCount", dto.getReviewsCount());
                return map;
              })
              .collect(Collectors.toList());
      model.addAttribute("tours", tours);
      model.addAttribute("curPage", tourList.getCurPage());
      model.addAttribute("curPageSize", tourList.getCurPageSize());
      model.addAttribute("totalElements", tourList.getTotalElements());
      model.addAttribute("totalPages", tourList.getTotalPages());
      return "feed";
    } else {
      return "dashboard";
    }
  }
}
