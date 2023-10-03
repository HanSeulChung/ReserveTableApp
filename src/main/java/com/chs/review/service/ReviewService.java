package com.chs.review.service;

import com.chs.review.dto.ReviewDto;
import com.chs.review.dto.ReviewInput;

import java.util.List;

public interface ReviewService {

    /**
     * 리뷰 등록
     */
    ReviewDto registerReview(ReviewInput parameter, Long reservationId, String userId);
    /**
     * 리뷰 수정
     */
    ReviewDto updateReview(ReviewInput parameter, Long reviewId, String userId);

    /**
     * 리뷰 삭제 By User
     */
    void deleteReviewByUser(Long reviewId, String userId);

    /**
     * 리뷰 조회 by 사용자 페이지
     */
    List<ReviewDto> viewByUser(String userId);

    List<ReviewDto> viewByUser(String userId, Long reviewId);

    /**
     * 리뷰 조회 by 가게별
     */
    List<ReviewDto> viewByStore(Long storeId);

    /**
     * 리뷰 삭제 By Owner
     */
    void deleteReviewByOwner(Long reviewId, String ownerId);
}
