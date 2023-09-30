package com.chs.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth/owner")
@RequiredArgsConstructor
public class ReviewForOwnerController {

    @GetMapping("/get/review")
    public ResponseEntity<?> getReview(@RequestParam Long reviewId,
                                       @RequestHeader("Authorization") String token) {
        return null;
    }

    @DeleteMapping("/delete/review")
    public ResponseEntity<?> deleteReview(@RequestParam Long reviewId,
                                          @RequestHeader("Authorization") String token) {
        return null;
    }
}
