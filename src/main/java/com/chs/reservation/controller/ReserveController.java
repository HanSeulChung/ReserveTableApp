package com.chs.reservation.controller;


import com.chs.member.user.entity.User;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.service.ReservationService;
import com.chs.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class ReserveController {

    private final ReservationService reservationService;
    private final TokenProvider tokenProvider;
    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody ReservationInput request,
                                     @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = this.reservationService.reserve(request, userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/reservation")
    public ResponseEntity<?> delete(@RequestParam Long reservationId,
                                    @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        this.reservationService.delete(reservationId, userId);
        return ResponseEntity.ok("해당 예약이 삭제되었습니다.");
    }
}
