package com.chs.review.repository;

import com.chs.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long> {



    @Transactional
    void deleteById(Long reviewId);
}
