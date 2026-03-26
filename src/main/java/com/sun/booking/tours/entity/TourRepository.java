package com.sun.booking.tours.entity;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByCategoryId(Long categoryId);

    List<Tour> findByTitleContainingIgnoreCase(String title);

    @Transactional
    @Modifying
    @Query("UPDATE Tour t SET t.deletedAt = CURRENT_TIMESTAMP WHERE t.id = :id")
    int deleteTourById(Long id);

    @Query("SELECT t FROM Tour t WHERE t.deletedAt IS NULL")
    Page<Tour> findAllActive(Pageable pageable);
}
