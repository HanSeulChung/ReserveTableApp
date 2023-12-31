package com.chs.review.controller;

import com.chs.review.dto.ReviewInput;
import com.chs.review.service.ReviewService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReviewForUserController {

    private final ReviewService reviewService;
    private final TokenAuthenticationProvider tokenProvider;

    @PostMapping("/register/review")
    public ResponseEntity<?> registerMyReview(@RequestParam Long reservationId,
                                              @RequestHeader("Authorization") String token,
                                              @RequestBody ReviewInput parameter) {
        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = reviewService.registerReview(parameter, reservationId, userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/review")
    public ResponseEntity<?> getMyReview(@RequestParam Long reviewId,
                                         @RequestHeader("Authorization") String token) {

        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = reviewService.viewByUser(userId, reviewId);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/review")
    public ResponseEntity<?> deleteMyReview(@RequestParam Long reviewId,
                                            @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        reviewService.deleteReviewByUser(reviewId, userId);

        return ResponseEntity.ok("해당 리뷰가 삭제되었습니다.");
    }

    @PostMapping("update/review")
    public ResponseEntity<?> updateMyReview(@RequestParam Long reviewId,
                                            @RequestHeader("Authorization") String token,
                                            @RequestBody ReviewInput parameter) {

        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = reviewService.updateReview(parameter, reviewId, userId);

        return ResponseEntity.ok(result);
    }
}
