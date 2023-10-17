package com.chs.review.controller;

import com.chs.review.service.ReviewService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/auth/owner")
@RequiredArgsConstructor
public class ReviewForOwnerController {

    private final ReviewService reviewService;
    private final TokenAuthenticationProvider tokenProvider;
    @GetMapping("/get/review")
    public ResponseEntity<?> getReview(@RequestParam Long reviewId,
                                       @RequestHeader("Authorization") String token) {
        return null;
    }

    @DeleteMapping("/delete/review")
    public ResponseEntity<?> deleteReview(@RequestParam Long reviewId,
                                          @RequestHeader("Authorization") String token) {
        String ownerId = tokenProvider.getUserId(token.substring("Bearer ".length()));
         reviewService.deleteReviewByOwner(reviewId, ownerId);

        return ResponseEntity.ok("해당 리뷰가 삭제되었습니다.");
    }
}
