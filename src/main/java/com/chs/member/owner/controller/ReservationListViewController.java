package com.chs.member.owner.controller;


import com.chs.reservation.entity.Reservation;
import com.chs.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/admin/reservation")
@RequiredArgsConstructor
public class ReservationListViewController {
    private final ReservationService reservationService;
    @GetMapping("/all")
    public ResponseEntity<?> viewAllStore(final Pageable pageable) {
        Page<Reservation> reservations = this.reservationService.getAllReservations(pageable);
        return ResponseEntity.ok(reservations);
    }


}
