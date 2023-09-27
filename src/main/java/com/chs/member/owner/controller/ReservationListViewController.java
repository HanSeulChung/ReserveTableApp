package com.chs.member.owner.controller;


import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.service.StoreService;
import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.entity.Reservation;
import com.chs.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth/owner/reservation")
@RequiredArgsConstructor
public class ReservationListViewController {
    private final ReservationService reservationService;
    private final StoreService storeService;

//    @GetMapping("{ownerId}/all")
//    public ResponseEntity<?> viewAllReservation(@PathVariable String ownerId) {
//        List<StoreDto> storeByOwnerId = this.storeService.getStoreByOwnerId(ownerId);
//        Map<Long, List<ReservationDto>> reservationMapByOwnerId = this.reservationService.getReservationByOwnerId(ownerId);
//        return ResponseEntity.ok(reservationMapByOwnerId);
//    }

    @GetMapping("{ownerId}/{storeId}")
    public ResponseEntity<?> viewReservation(@PathVariable String ownerId,
                                             @PathVariable Long storeId) {
        var result = this.reservationService.getReservationByOwnerIdAndStoreId(ownerId, storeId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> reservationApproval(@RequestParam Long reservationId) {
        var result = reservationService.approvalReservation(reservationId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refuse")
    public ResponseEntity<?> reservationRefuse(@RequestParam Long reservationId) {
        var result = reservationService.refuseReservation(reservationId);
        return ResponseEntity.ok(result);
    }

}
