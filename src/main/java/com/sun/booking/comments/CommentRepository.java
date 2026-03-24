package com.sun.booking.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByReviewId(Long reviewId);

    List<Comment> findByUserId(Long userId);

    List<Comment> findByParentCommentId(Long parentCommentId);
}
