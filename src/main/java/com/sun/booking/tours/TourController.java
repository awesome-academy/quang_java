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
import com.sun.booking.tours.dto.TourDTO;
import com.sun.booking.tours.entity.TourRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Tour Management", description = "Endpoints for managing tours, including creation, retrieval, updating, and deletion of tour information")
@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {
  private final TourService tourService;

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/")
  public BaseResponse<ListResponse> getListTour(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
    ListResponse result = tourService.getAllTours(page, size);
      return new SuccessResponse<ListResponse>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create")
  public BaseResponse<TourDTO> createTour(@RequestBody TourDTO input) {
    TourDTO result = tourService.createTour(input);
    return new SuccessResponse<TourDTO>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/detail/{id}")
  public BaseResponse<TourDTO> getTourDetail(@PathVariable Long id) {
    TourDTO result = tourService.getTourDetail(id);
    return new SuccessResponse<TourDTO>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/update/{id}")
  public BaseResponse<TourDTO> updateTour(@PathVariable Long id, @RequestBody TourDTO input) {
    TourDTO result = tourService.updateTour(id, input);
    return new SuccessResponse<TourDTO>(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  public BaseResponse<Boolean> deleteTour(@PathVariable Long id) {
    boolean isDeleted = tourService.deleteTour(id);
    return new SuccessResponse<Boolean>(isDeleted);
  }
}
