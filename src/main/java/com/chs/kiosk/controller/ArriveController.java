package com.chs.kiosk.controller;


import com.chs.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class ArriveController {
    private final ReservationService reservationService;
    @GetMapping("/{userId}")
    public ResponseEntity<?> viewReservationOnKiosk(
                        @RequestParam Long storeId,
                        @PathVariable String userId) {
        var result = this.reservationService.getReservationByUserIdOnKiosk(userId, storeId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/arrive")
    public ResponseEntity<?> arriveOnKiosk(
            @RequestParam Long reservationId) {
        var result = this.reservationService.arriveOnStore(reservationId);
        return ResponseEntity.ok(result);
    }
}
