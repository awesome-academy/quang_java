package com.sun.booking.tours;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.tours.dto.TourDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {
  private final TourRepository tourRepository;
  private final ModelMapper modelMapper;

  public ListResponse getAllTours(int page, int size) {
    PageRequest pageable = PageRequest.of(page, size);
    Page<Tour> tourPage = tourRepository.findAll(pageable);
    List<TourDTO> content = tourPage.map(tour -> modelMapper.map(tour, TourDTO.class)).getContent();
    return ListResponse.builder()
            .content(content)
            .curPage(tourPage.getNumber())
            .curPageSize(size)
            .totalElements(tourPage.getTotalElements())
            .totalPages(tourPage.getTotalPages())
            .build();
  }
}
