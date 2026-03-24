package com.sun.booking.tours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByCategoryId(Long categoryId);

    List<Tour> findByTitleContainingIgnoreCase(String title);
}
