package com.chs.reservation.controller;


import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class ReserveController {

    private final ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody ReservationInput request) {
        var result = this.reservationService.reserve(request);
        return ResponseEntity.ok(result);
    }
}
