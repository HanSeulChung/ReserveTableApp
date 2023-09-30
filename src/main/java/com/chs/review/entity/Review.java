package com.chs.review.entity;

import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.entity.Store;
import com.chs.member.user.entity.User;
import com.chs.reservation.entity.Reservation;
import com.chs.review.dto.ReviewDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "REVIEW")
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private String review;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reservation_id")
    private Reservation reservation;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public static Review toEntity(ReviewDto reviewDto) {
        return Review.builder()
                .score(reviewDto.getScore())
                .review(reviewDto.getReview())
                .regDt(reviewDto.getRegDt())
                .udtDt(reviewDto.getUdtDt())
                .build();
    }

}
