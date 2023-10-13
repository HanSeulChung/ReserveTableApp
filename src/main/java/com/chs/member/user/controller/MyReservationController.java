package com.chs.member.user.controller;

import com.chs.reservation.service.ReservationService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/myreservation")
@RequiredArgsConstructor
public class MyReservationController {
    private final ReservationService reservationService;
    private final TokenAuthenticationProvider tokenProvider;
    @GetMapping()
    public ResponseEntity<?> viewMyReservation(@RequestHeader("Authorization") String token) {
        String loginUserId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = this.reservationService.getReservationsByUserId(loginUserId);

        return ResponseEntity.ok(result);
    }

}
