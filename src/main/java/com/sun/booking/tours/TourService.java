package com.sun.booking.tours;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.tours.dto.TourDTO;
import com.sun.booking.tours.entity.Tour;
import com.sun.booking.tours.entity.TourRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {
  private final TourRepository tourRepository;
  private final ModelMapper modelMapper;

  public ListResponse getAllTours(int page, int size) {
    // start from page 1 for client, but PageRequest starts from 0
    page = page > 0 ? page - 1 : 0;
    PageRequest pageable = PageRequest.of(page, size);
    Page<Tour> tourPage = tourRepository.findAll(pageable);
    List<TourDTO> content = tourPage.map(tour -> modelMapper.map(tour, TourDTO.class)).getContent();
    return ListResponse.builder()
            .content(content)
            .curPage(tourPage.getNumber() + 1) // adjust for client page numbering
            .curPageSize(size)
            .totalElements(tourPage.getTotalElements())
            .totalPages(tourPage.getTotalPages())
            .build();
  }
}
