package com.sun.booking.userreviews.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

    Page<UserReview> findByUserId(Long userId, Pageable pageable);

    List<UserReview> findByTourId(Long tourId);
}
