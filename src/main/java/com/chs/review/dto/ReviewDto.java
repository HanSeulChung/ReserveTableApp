package com.chs.review.dto;

import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.entity.Store;
import com.chs.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewDto {

    private String userId;
    private Long storeId;
    private String storeName;
    private Long reservationId;
    private int score;
    private String review;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    public static ReviewDto of(Review review) {

        return ReviewDto.builder()
                .userId(review.getReservation().getUser().getUserId())
                .storeId(review.getReservation().getStore().getId())
                .storeName(review.getReservation().getStore().getStoreName())
                .reservationId(review.getReservation().getId())
                .score(review.getScore())
                .review(review.getReview())
                .regDt(review.getRegDt())
                .udtDt(review.getUdtDt())
                .build();
    }

    public static ReviewDto fromInput(ReviewInput reviewInput) {
        return ReviewDto.builder()
                .score(reviewInput.getScore())
                .review(reviewInput.getReview())
                .regDt(LocalDateTime.now())
                .build();
    }
}
