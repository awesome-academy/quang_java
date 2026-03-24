package com.sun.booking.userreviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

    List<UserReview> findByUserId(Long userId);

    List<UserReview> findByTourId(Long tourId);
}
