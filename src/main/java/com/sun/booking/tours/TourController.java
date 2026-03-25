package com.sun.booking.tours;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.booking.common.httpresponse.BaseResponse;
import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.common.httpresponse.SuccessResponse;
import com.sun.booking.tours.entity.Tour;
import com.sun.booking.tours.entity.TourRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Tour Management", description = "Endpoints for managing tours, including creation, retrieval, updating, and deletion of tour information")
@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {
  private final TourService tourService;
  private final TourRepository tourRepository;

  // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  // @GetMapping("/")
  // public BaseResponse<ListResponse> getListTour(@RequestParam(defaultValue = "0") int page,
  //                           @RequestParam(defaultValue = "5") int size) {
  //   ListResponse result = tourService.getAllTours(page, size);
  //     return new SuccessResponse<ListResponse>(result);
  // }
  
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/")
  public Map<String, Object> getListTour(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
    ListResponse result = tourService.getAllTours(page, size);
    //   return new SuccessResponse<ListResponse>(result);
    Map<String, Object> response = new HashMap<>();
    response.put("items", result.getContent());
    response.put("total", result.getTotalElements());
    return response;
  }
  
}
