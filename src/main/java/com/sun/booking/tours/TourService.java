package com.sun.booking.tours;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sun.booking.categories.CategoryService;
import com.sun.booking.categories.entity.Category;
import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.tours.dto.TourDTO;
import com.sun.booking.tours.entity.Tour;
import com.sun.booking.tours.entity.TourRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {
  private final TourRepository tourRepository;
  private final CategoryService categoryService;
  private final ModelMapper modelMapper;

  public ListResponse getAllTours(int page, int size) {
    // start from page 1 for client, but PageRequest starts from 0
    page = page > 0 ? page - 1 : 0;
    PageRequest pageable = PageRequest.of(page, size);
    Page<Tour> tourPage = tourRepository.findAllActive(pageable);
    List<TourDTO> content = tourPage.map(tour -> modelMapper.map(tour, TourDTO.class)).getContent();
    return ListResponse.builder()
            .content(content)
            .curPage(tourPage.getNumber() + 1) // adjust for client page numbering
            .curPageSize(size)
            .totalElements(tourPage.getTotalElements())
            .totalPages(tourPage.getTotalPages())
            .build();
  }


  public TourDTO getTourDetail(Long id) {
    Tour tour = tourRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
    return modelMapper.map(tour, TourDTO.class);
  }

  @Transactional(rollbackOn = RuntimeException.class)
  public TourDTO createTour(TourDTO input) {
    Tour tour = new Tour();
    tour.setTitle(input.getTitle());
    tour.setDescription(input.getDescription());
    tour.setPrice(input.getPrice());

    Category existedCategory = categoryService.getCategoryDetailById(input.getCategoryId());
    tour.setCategory(existedCategory);

    Tour savedTour = tourRepository.save(tour);
    return modelMapper.map(savedTour, TourDTO.class);
  }

  @Transactional(rollbackOn = RuntimeException.class)
  public TourDTO updateTour(Long id, TourDTO input) {
    Tour tour = tourRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));

    tour.setTitle(input.getTitle());
    tour.setDescription(input.getDescription());
    tour.setPrice(input.getPrice());
    tour.setImageUrl(input.getImageUrl());

    if (!tour.getCategory().getId().equals(input.getCategoryId())) {
      Category existedCategory = categoryService.getCategoryDetailById(input.getCategoryId());
      tour.setCategory(existedCategory);
    }

    Tour updatedTour = tourRepository.save(tour);
    return modelMapper.map(updatedTour, TourDTO.class);
  }

  @Transactional(rollbackOn = RuntimeException.class)
  public boolean deleteTour(Long id) {
    int deletedCount = tourRepository.deleteTourById(id);
    return deletedCount > 0;
  }
}
