package com.chs.reservation.controller;

import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.service.ReservationService;
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
public class ReservationForUserController {

    private final ReservationService reservationService;
    private final TokenAuthenticationProvider tokenProvider;
    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody ReservationInput request,
                                     @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = this.reservationService.reserve(request, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/myreservations")
    public ResponseEntity<?> viewMyreservation( @RequestHeader("Authorization") String token){
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        var result = this.reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(result);

    }
    @DeleteMapping("/delete/reservation")
    public ResponseEntity<?> delete(@RequestParam Long reservationId,
                                    @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        this.reservationService.delete(reservationId, userId);
        return ResponseEntity.ok("해당 예약이 삭제되었습니다.");
    }

    @PostMapping("/update/reservation")
    public ResponseEntity<?> update(@RequestParam Long reservationId,
                                    @RequestBody ReservationInput request,
                                    @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        var result = this.reservationService.update(request, reservationId, userId);
        return ResponseEntity.ok(result);
    }

}
