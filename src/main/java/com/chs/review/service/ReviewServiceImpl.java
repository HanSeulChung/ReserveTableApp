package com.chs.review.service;

import com.chs.exception.Impl.NoReservationException;
import com.chs.exception.Impl.NoReviewException;
import com.chs.exception.Impl.NoUserIdException;
import com.chs.exception.Impl.UnmatchReservationUserException;
import com.chs.member.user.entity.User;
import com.chs.member.user.repository.UserRepository;
import com.chs.reservation.entity.Reservation;
import com.chs.reservation.repository.ReservationRepository;
import com.chs.review.dto.ReviewDto;
import com.chs.review.dto.ReviewInput;
import com.chs.review.entity.Review;
import com.chs.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public ReviewDto registerReview(ReviewInput parameter, Long reservationId, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoUserIdException());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        Review reviewSave = Review.toEntity(ReviewDto.fromInput(parameter));
        reviewSave.setReservation(reservation);
        reviewRepository.save(reviewSave);
        return ReviewDto.of(reviewSave);
    }

    @Override
    public ReviewDto updateReview(ReviewInput parameter, Long reviewId, String userId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoReviewException());

        if (!review.getReservation().getUser().getUserId().equals(userId)) {
            throw new UnmatchReservationUserException();
        }

        return ReviewDto.of(reviewRepository.save(
                Review.builder()
                        .id(review.getId())
                        .reservation(review.getReservation())
                        .score(parameter.getScore())
                        .review(parameter.getReview())
                        .regDt(review.getRegDt())
                        .udtDt(LocalDateTime.now())
                        .build()
        ));
    }

    @Override
    public void deleteReview(Long reviewId, String userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoReviewException());

        if (!review.getReservation().getUser().getUserId().equals(userId)) {
            throw new UnmatchReservationUserException();
        }

        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewDto> viewByUser(String userId) {
        return null;
    }

    @Override
    public List<ReviewDto> viewByUser(String userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoReviewException());

        if (!review.getReservation().getUser().getUserId().equals(userId)) {
            throw new UnmatchReservationUserException();
        }

        return null;
    }

    @Override
    public List<ReviewDto> viewByStore(Long storeId) {
        return null;
    }
}
